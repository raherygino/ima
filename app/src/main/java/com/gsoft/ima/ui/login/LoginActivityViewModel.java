package com.gsoft.ima.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.ui.signup.SignupActivity;

public class LoginActivityViewModel extends BaseObservable {

    private final Context context;
    private final Activity activity;
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public LoginActivityViewModel(Context mContext) {
        this.context = mContext;
        this.activity = (Activity) context;
    }

    public void loginListener() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void signupListener() {
        Intent intent = new Intent(activity, SignupActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
