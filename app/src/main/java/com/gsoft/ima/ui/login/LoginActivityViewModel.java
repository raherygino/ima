package com.gsoft.ima.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.gsoft.ima.R;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.di.dialog.PromptDialog;
import com.gsoft.ima.models.User;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.ui.signup.SignupActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                        /*
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();*/
    }

    public void signupListener() {
        Intent intent = new Intent(activity, SignupActivity.class);
        activity.startActivity(intent);
        activity.finish();
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
