package com.gsoft.ima.ui.main.send;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.api.FetchData;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.databinding.FragmentSendBinding;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.utils.UserLogged;
import com.gsoft.ima.utils.Utils;

import static com.gsoft.ima.constants.main.MainConstants.*;
import static com.gsoft.ima.constants.main.TransactionConstants.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final FragmentSendBinding binding;
    private final User user;
    private Transaction transaction;
    private ProgressDialog dialogLoading;

    public SendViewModel(Context context, FragmentSendBinding binding) {
        this.binding = binding;
        this.context = context;
        this.user = UserLogged.data(context);
    }

    public void onChangeType() {
        PopupMenu popupMenu = new PopupMenu(context, binding.sendBy);
        popupMenu.getMenuInflater().inflate(R.menu.type_send, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                binding.sendBy.setText(menuItem.getTitle());
                if (menuItem.getItemId() == R.id.data) {
                    binding.layoutName.setVisibility(View.GONE);
                    binding.name.setText("none");
                } else {
                    binding.layoutName.setVisibility(View.VISIBLE);
                    binding.name.setText(EMPTY);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void send() {
        int amount = 0;
        String amountValue = binding.amount.getText().toString();

        if (!amountValue.equals(EMPTY))  {
            amount = Integer.parseInt(amountValue);
        }

        transaction = new Transaction(amount);
        transaction.nameSender = user.lastname;
        transaction.numSender = user.phone;
        transaction.method = binding.sendBy.getText().toString();
        transaction.nameReceiver = binding.name.getText().toString();
        transaction.numReceiver = binding.phone.getText().toString();
        transaction.status = STAT_PENDING;
        transaction.date = Utils.DateSQLFormatNow();
        transaction.ipAddress = Utils.getIpAddress(context);
        DatabaseHelper db = new DatabaseHelper(context);

        if (validation(transaction)) {
            String method = transaction.method;
            if (method.equals(context.getString(R.string.qr_code))) {
                String title = EMPTY;
                String message = context.getString(R.string.qr_code_created);
                Utils.createQrCode(TransactionToString(transaction), binding.qrImage);
                AlertDialog dialog = new AlertDialog(context, title, message);
                dialog.show();
            } else if (method.equals(context.getString(R.string.network))){
                dialogLoading = new ProgressDialog(context);
                dialogLoading.setMessage(context.getString(R.string.loading));
                dialogLoading.setCancelable(false);
                dialogLoading.show();
                RetrofitClient.createTransaction(transaction)
                        .enqueue(new enqueue(SEND));
            } else {
                if (db.insertTransaction(transaction) != -1) {
                    AlertDialog dialog = new AlertDialog(context, EMPTY, IS_CREATED);
                    dialog.show();
                } else {
                    AlertDialog dialog = new AlertDialog(context, EMPTY, context.getString(R.string.error));
                    dialog.show();
                }
            }
        } else {
            Toast.makeText(context, context.getString(R.string.check_the_form), Toast.LENGTH_LONG).show();
        }
    }

    private String TransactionToString(Transaction transaction) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(NAME_SENDER, transaction.nameSender);
            jsonObject.put(NUM_SENDER, transaction.numSender);
            jsonObject.put(NAME_RECEIVER, transaction.nameReceiver);
            jsonObject.put(NUM_RECEIVER, transaction.numReceiver);
            jsonObject.put(IP_SENDER, transaction.ipAddress);
            jsonObject.put(AMOUNT, transaction.amount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private boolean validation(Transaction transaction) {
        boolean isValidate = true;
        if (transaction.nameReceiver.length() < 3) {
            isValidate = false;
            binding.name.setError(context.getString(R.string.value_too_short));
        }

        if (Utils.isNotOnlyAlphabet(transaction.nameReceiver)) {
            isValidate = false;
            binding.name.setError(context.getString(R.string.error_char_spec));
        }

        if (transaction.numReceiver.length() != 10) {
            isValidate = false;
            binding.phone.setError(context.getString(R.string.error_phone));
        }

        if (transaction.numReceiver.equals(user.phone)) {
            isValidate = false;
            binding.phone.setError(context.getString(R.string.error_phone));
        }

        if (transaction.amount < 50) {
            isValidate = false;
            binding.amount.setError(context.getString(R.string.value_too_short));
        }

        if (transaction.amount > user.balance) {
            isValidate = false;
            String message = context.getString(R.string.error_balance);
            binding.amount.setError(message);
            AlertDialog dialog = new AlertDialog(context, message, EMPTY);
            dialog.show();
        }

        if (!binding.password.getText().toString().equals(user.password)) {
            isValidate = false;
            binding.password.setError(context.getString(R.string.password_not_match));
        }

        return isValidate;
    }

    class enqueue implements Callback<ResponseBody> {

        private String type;

        public enqueue(String type) {
            this.type = type;


        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            dialogLoading.cancel();
                if (response.isSuccessful()) {
                    DatabaseHelper db = new DatabaseHelper(context);
                    String result = EMPTY;
                    try {
                        result = response.body().source().readUtf8();
                        JSONObject object = new JSONObject(result);

                        if (type.equals(SEND)) {
                            if (object.getString(MESSAGE).contains(STAT_SENT)) {
                                RetrofitClient.totalPending(user.phone)
                                        .enqueue(new enqueue(TOTAL));
                            }
                        }else if (type.equals(TOTAL)) {
                            AlertDialog dialog = new AlertDialog(context, EMPTY, context.getString(R.string.amount_sent));
                            dialog.show();
                            binding.name.setText(EMPTY);
                            binding.phone.setText(EMPTY);
                            binding.amount.setText(EMPTY);
                            binding.password.setText(EMPTY);
                            db.addAmountPending(object.getInt(TOTAL));
                            FetchData.getDataUserByPhone(context, user.phone);
                            FetchData.getPendingSender(context, user.phone);
                        } else if (type.equals(ALL_PENDING)) {
                            FetchData.getPendingSender(context, user.phone);
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        AlertDialog dialog = new AlertDialog(context, EMPTY, e.getMessage()+" "+result);
                        dialog.show();
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
                }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            dialogLoading.cancel();
            AlertDialog dialog = new AlertDialog(context, EMPTY, t.getMessage());
            dialog.show();
        }
    }
}
