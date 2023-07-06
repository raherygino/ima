package com.gsoft.ima.ui.main.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.api.FetchData;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.main.MainActivity;

import dmax.dialog.SpotsDialog;

public class HomeViewModel extends ViewModel {

    Context context;
    public User user;

    public HomeViewModel(Context context) {
        this.context = context;
        DatabaseHelper db = new DatabaseHelper(context);
        this.user = db.User();
    }

    public void logout() {
        DatabaseHelper db = new DatabaseHelper(context);
        if (db.deleteUser() != -1) {
            Intent intent = new Intent(context, AuthActivity.class);
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.finish();
        } else {
            AlertDialog dialog = new AlertDialog(context,context.getString(R.string.error),context.getString(R.string.error_logout));
            dialog.show();
        }
    }

    public void refresh() {

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("Loading");
        dialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FetchData.getDataUserByPhone(context, user.phone);
                FetchData.getPendingSender(context, user.phone);
                MainActivity mainActivity = (MainActivity) context;
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setFragment(new HomeFragment());
                        dialog.dismiss();
                    }
                });
            }
        });
        thread.start();
    }
}
