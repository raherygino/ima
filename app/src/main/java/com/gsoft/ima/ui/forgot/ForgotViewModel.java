package com.gsoft.ima.ui.forgot;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.databinding.FragmentForgotPassBinding;
import com.gsoft.ima.databinding.FragmentLoginBinding;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.login.LoginFragment;
import com.gsoft.ima.ui.resetpass.ResetPasswordFragment;
import com.gsoft.ima.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gsoft.ima.constants.main.FormConstants.*;
import static com.gsoft.ima.constants.main.MainConstants.*;

public class ForgotViewModel extends ViewModel {
    private final Context context;
    private final AuthActivity activity;
    private FragmentForgotPassBinding binding;

    public ForgotViewModel(Context context, FragmentForgotPassBinding binding) {
        this.context = context;
        this.activity = (AuthActivity) context;
        this.binding = binding;
    }

    public void send(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.setError(context.getString(R.string.email_invalid));
        } else {
            Call<ResponseBody> send = RetrofitClient.forgotPassword(email);
            send.enqueue(new CallBack(SEND));
        }
    }

    public void reset(String email, String code) {
        boolean isValidate = true;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValidate = false;
            binding.email.setError(context.getString(R.string.email_invalid));
        }

        if (code.length() != 6) {
            isValidate = false;
            binding.resetCode.setError(context.getString(R.string.code_invalid));
        }

        if (isValidate) {
            Call<ResponseBody> checkCode = RetrofitClient.checkCode(email, code);
            checkCode.enqueue(new CallBack(EMPTY));
        }
    }

    class CallBack implements Callback<ResponseBody> {

        String NameCall;
        ProgressDialog progressDialog;

        public CallBack(String name) {
            NameCall = name;
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(context.getString(R.string.loading));
            progressDialog.show();
        }

        public void onResultSent(String result, String code) {
            if (result.contains(CODE)) {
                binding.message.setVisibility(View.VISIBLE);
                binding.btnReset.setVisibility(View.VISIBLE);
                binding.layoutResetCode.setVisibility(View.VISIBLE);
                Utils.setOnHoverLabel(binding.resetCode);
                binding.resetCode.setText(code);
                binding.btnSend.setText(context.getString(R.string.resend));
            } else {
                AlertDialog dialog = new AlertDialog(context, EMPTY, result);
                dialog.show();
            }
        }

        public void onResultReset(String result) {
             if (result.contains(SUCCESS)) {
                activity.setFragment(new ResetPasswordFragment());
                activity.setSharedEmail(binding.email.getText().toString());
             } else {
                AlertDialog dialog = new AlertDialog(context, EMPTY, result);
                dialog.show();
             }
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            progressDialog.hide();
            if (response.isSuccessful()) {
                try {
                    String result = response.body().source().readUtf8();
                    JSONObject object = new JSONObject(result);
                    if (NameCall.equals(SEND)) {
                        onResultSent(result, object.getString(CODE));
                    } else {
                        onResultReset(result);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    String message = e.getMessage();
                    if (message.contains(CODE)) {
                        message = context.getString(R.string.email_not_found);
                    }
                    AlertDialog dialog = new AlertDialog(context, EMPTY, message);
                    dialog.show();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            progressDialog.hide();
            String message = t.getMessage();
            AlertDialog dialog;
            assert message != null;
            if (message.contains(TIMED_OUT) || message.contains(HOST_NOT_FOUND)) {
                dialog = new AlertDialog(context, EMPTY, context.getString(R.string.error_connection));
            } else {
                dialog = new AlertDialog(context, EMPTY, message);
            }
            dialog.show();
        }
    }
}
