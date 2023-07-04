package com.gsoft.ima.ui.main.send;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;
import android.widget.PopupMenu;

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
        Transaction transaction = new Transaction(Integer.parseInt(binding.amount.getText().toString()));
        transaction.nameSender = user.lastname;
        transaction.numSender = user.phone;
        transaction.method = binding.sendBy.getText().toString();
        transaction.nameReceiver = binding.name.getText().toString();
        transaction.numReceiver = binding.phone.getText().toString();
        transaction.status = STAT_PENDING;
        transaction.date = Utils.DateSQLFormatNow();
        DatabaseHelper db = new DatabaseHelper(context);
        if (db.insertTransaction(transaction) != -1) {
            AlertDialog dialog = new AlertDialog(context, EMPTY, "Created");
            dialog.show();
        } else {
            AlertDialog dialog = new AlertDialog(context, EMPTY, "Error");
            dialog.show();
        }

    }
}
