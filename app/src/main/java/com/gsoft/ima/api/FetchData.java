package com.gsoft.ima.api;

import static com.gsoft.ima.constants.main.TransactionConstants.ALL_PENDING;
import static com.gsoft.ima.constants.main.TransactionConstants.AMOUNT;
import static com.gsoft.ima.constants.main.TransactionConstants.NAME_RECEIVER;
import static com.gsoft.ima.constants.main.TransactionConstants.NAME_SENDER;
import static com.gsoft.ima.constants.main.TransactionConstants.NUM_RECEIVER;
import static com.gsoft.ima.constants.main.TransactionConstants.NUM_SENDER;

import android.content.Context;
import android.widget.Toast;

import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;

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
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(object.getString("all"));
                db.deleteTransactionNetwork();
                for (int i = 0 ; i < jsonArray.length(); i++ ) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    Transaction transaction1 = new Transaction(item.getInt(AMOUNT));
                    transaction1.method = "Network";
                    transaction1.numSender = item.getString(NUM_SENDER);
                    transaction1.nameSender = item.getString(NAME_SENDER);
                    transaction1.numReceiver = item.getString(NUM_RECEIVER);
                    transaction1.nameReceiver = item.getString(NAME_RECEIVER);
                    transaction1.status = item.getString("status");
                    db.insertTransaction(transaction1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
