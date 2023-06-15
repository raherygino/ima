package com.gsoft.ima.ui.login;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.gsoft.ima.R;
import com.gsoft.ima.di.dialog.PromptDialog;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.signup.SignupFragment;

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
        authActivity.setFragment(new SignupFragment());
    }

    public void forgotPassListener() {
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
        dialog.show();
    }
}
