package com.gsoft.ima.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentManager;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentLoginBinding;
import com.gsoft.ima.di.dialog.PromptDialog;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.register.RegisterFragment;

public class LoginViewModel extends BaseObservable {

    private final Context context;
    private final Activity activity;
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    private FragmentLoginBinding binding;

    public LoginViewModel(LoginFragment fragment) {
        this.context = fragment.getContext();
        this.activity = (Activity) context;
        this.binding = fragment.binding;
    }

    public void loginListener() {
        phone.set(binding.phone.getText().toString());
        password.set(binding.password.getText().toString());
        Toast.makeText(context, password.get()+" "+phone.get(), Toast.LENGTH_LONG).show();
    }

    public void registerListener() {
        AuthActivity authActivity = (AuthActivity) activity;
        authActivity.setFragment(new RegisterFragment());
    }

    public void forgotPassListener() {
        PromptDialog dialog = new PromptDialog(context,
                activity.getString(R.string.forgot),
                activity.getString(R.string.forgot_pass_info),
                activity.getString(R.string.email)
        );
        dialog.btnOk.setText(activity.getString(R.string.send));
        dialog.btnOk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onClick(View view) {
                String email = dialog.editText.getText().toString();
                Toast.makeText(context,  context.getString(R.string.email_sent_to, email), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
