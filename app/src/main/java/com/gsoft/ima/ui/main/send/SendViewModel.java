package com.gsoft.ima.ui.main.send;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;

public class SendViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final FragmentSendBinding binding;
    private final User user;

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

        Transaction transaction = new Transaction(amount);
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
            if (transaction.method.equals(context.getString(R.string.qr_code))) {
               /* MainActivity activity = (MainActivity) context;
                Socket socket = activity.socket;*/
                String title = EMPTY;
                String message = context.getString(R.string.qr_code_created);/*
                if (socket == null) {
                    title = context.getString(R.string.error_con_to_phone);
                    message = context.getString(R.string.message_create_client);
                } else {*/
                    Utils.createQrCode(TransactionToString(transaction), binding.qrImage);
                //}
                AlertDialog dialog = new AlertDialog(context, title, message);
                dialog.show();
            } else {
                if (db.insertTransaction(transaction) != -1) {
                    AlertDialog dialog = new AlertDialog(context, EMPTY, "Created");
                    dialog.show();
                } else {
                    AlertDialog dialog = new AlertDialog(context, EMPTY, "Error");
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
}
