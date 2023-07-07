package com.gsoft.ima.api;

import static com.gsoft.ima.constants.main.FormConstants.CREATED_AT;
import static com.gsoft.ima.constants.main.MainConstants.*;
import static com.gsoft.ima.constants.main.TransactionConstants.*;

import android.content.Context;
import android.widget.Toast;

import com.gsoft.ima.R;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.utils.UserLogged;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchData {


    public static void getPendingSender(Context context, String phone) {
        RetrofitClient.getPendingSender(phone).enqueue(new Enqueue(context, ALL_PENDING));
    }

    public static void getDataUserByPhone(Context context, String phone) {
        RetrofitClient.getUser(phone).enqueue(new Enqueue(context, DATA_USER));
    }

    static class Enqueue implements Callback<ResponseBody> {

        private final Context context;
        private final String type;

        public Enqueue(Context context, String type) {
            this.context = context;
            this.type = type;
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                try {
                    String result = response.body().source().readUtf8();
                    JSONObject object = new JSONObject(result);
                    if (type.equals(ALL_PENDING)) {
                        getPendingSender(object);
                    } else if (type.equals(DATA_USER)) {
                        JSONObject data = new JSONObject(result);
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.deleteUser();
                        db.createUserByObject(data);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        public void getPendingSender(JSONObject object) {
            DatabaseHelper db = new DatabaseHelper(context);
            db.addCountPending(0);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(object.getString(ALL));
                db.deleteTransactionNetwork();
                int countPending = 0;
                for (int i = 0 ; i < jsonArray.length(); i++ ) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    Transaction transaction1 = new Transaction(item.getInt(AMOUNT));
                    transaction1.method = context.getString(R.string.network);
                    transaction1.numSender = item.getString(NUM_SENDER);
                    transaction1.nameSender = item.getString(NAME_SENDER);
                    transaction1.numReceiver = item.getString(NUM_RECEIVER);
                    transaction1.nameReceiver = item.getString(NAME_RECEIVER);
                    transaction1.status = item.getString(STATUS);
                    transaction1.id = item.getInt(ID_TRANSACTION);
                    transaction1.date = item.getString(CREATED_AT);
                    db.insertTransaction(transaction1);
                    if (UserLogged.data(context).firstname.equals(transaction1.nameReceiver) && transaction1.status.equals(STAT_PENDING)) {
                        countPending += 1;
                    }
                }
                db.addCountPending(countPending);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
