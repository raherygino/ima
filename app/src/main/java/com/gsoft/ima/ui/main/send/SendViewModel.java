package com.gsoft.ima.ui.main.send;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.DialogSendBinding;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.utils.UserLogged;
import com.gsoft.ima.utils.Utils;

import static com.gsoft.ima.constants.main.MainConstants.EMPTY;
import static com.gsoft.ima.constants.main.MainConstants.STAT_PENDING;

import org.json.JSONException;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class SendViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final DialogSendBinding binding;

    public SendViewModel(Context context, DialogSendBinding binding) {
        this.binding = binding;
        this.context = context;
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
        User user = UserLogged.data(context);
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


            // Create a new JSONObject
            JSONObject jsonObject = new JSONObject();

            // Add key-value pairs to the JSON object
            try {
                jsonObject.put("name_sender", transaction.nameSender);
                jsonObject.put("num_sender", transaction.numSender);
                jsonObject.put("name_receiver", transaction.nameReceiver);
                jsonObject.put("num_receiver", transaction.numReceiver);
                jsonObject.put("ip_sender", transaction.ipAddress);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Convert the JSON object to a string
            String jsonString = jsonObject.toString();

            Utils.createQrCode(jsonString, binding.qrImage);


            /*
                if (db.insertTransaction(transaction) != -1) {
                    AlertDialog dialog = new AlertDialog(context, EMPTY, "Created");
                    dialog.show();
                } else {
                    AlertDialog dialog = new AlertDialog(context, EMPTY, "Error");
                    dialog.show();
                }
              */


        } else {
            Toast.makeText(context, context.getString(R.string.check_the_form), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validation(Transaction transaction) {
        boolean isValidate = true;
        if (transaction.nameReceiver.length() < 3) {
            isValidate = false;
            binding.name.setError(context.getString(R.string.value_too_short));
        }

        if (transaction.numReceiver.length() != 10) {
            isValidate = false;
            binding.phone.setError(context.getString(R.string.error_phone));
        }

        if (transaction.amount < 50) {
            isValidate = false;
            binding.amount.setError(context.getString(R.string.value_too_short));
        }

        return isValidate;
    }
}
