package com.gsoft.ima.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;

import com.gsoft.ima.ui.login.LoginActivity;
import com.gsoft.ima.ui.main.MainActivity;

public class SignupActivityViewModel extends BaseObservable {

    private final Activity activity;

    public SignupActivityViewModel(Context context) {
        this.activity = (Activity) context;
    }

    public void signupListener() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void loginListener() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
