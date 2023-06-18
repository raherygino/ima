package com.gsoft.ima.ui.login;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.register.RegisterFragment;

public class LoginViewModel extends BaseObservable {

    private final Context context;
    private final Activity activity;
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    public LoginViewModel(Context mContext) {
        this.context = mContext;
        this.activity = (Activity) context;
    }

    public void loginListener() {
        //
    }

    public void signupListener() {
        AuthActivity authActivity = (AuthActivity) activity;
        authActivity.setFragment(new RegisterFragment());
    }

    public void forgotPassListener() {/*
        PromptDialog dialog = new PromptDialog(context,
                activity.getString(R.string.forgot),
                activity.getString(R.string.forgot_pass_info)
        );
        dialog.btnOk.setText(activity.getString(R.string.send));
        dialog.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = dialog.editText.getText().toString();
                Toast.makeText(context, "Email sent to "+email+" successfully", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();*/
    }
}
