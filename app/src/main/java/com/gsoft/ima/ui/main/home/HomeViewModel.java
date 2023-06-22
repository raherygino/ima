package com.gsoft.ima.ui.main.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.ui.auth.AuthActivity;

public class HomeViewModel extends ViewModel {

    Context context;

    public HomeViewModel(Context context) {
        this.context = context;
    }

    public void logout() {
        DatabaseHelper db = new DatabaseHelper(context);
        if (db.deleteUser() != -1) {
            Intent intent = new Intent(context, AuthActivity.class);
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.finish();
        } else {
            AlertDialog dialog = new AlertDialog(context,"Erreur","You are not logged");
            dialog.show();
        }
    }
}
