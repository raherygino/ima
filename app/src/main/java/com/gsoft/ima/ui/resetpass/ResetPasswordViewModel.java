package com.gsoft.ima.ui.resetpass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.databinding.FragmentResetPassBinding;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.forgot.ForgotFragment;
import com.gsoft.ima.ui.forgot.ForgotViewModel;
import com.gsoft.ima.ui.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gsoft.ima.constants.main.MainConstants.EMPTY;
import static com.gsoft.ima.constants.main.MainConstants.SEND;
import static com.gsoft.ima.constants.main.MainConstants.SINGLE_QUOTE;
import static com.gsoft.ima.constants.main.MainConstants.SUCCESS;

public class ResetPasswordViewModel extends ViewModel {
    FragmentResetPassBinding binding;
    @SuppressLint("StaticFieldLeak")
    AuthActivity authActivity;

    public ResetPasswordViewModel(Context context, FragmentResetPassBinding binding) {
        this.authActivity = (AuthActivity) context;
        this.binding = binding;
    }

    public void cancel() {
        authActivity.setFragment(new ForgotFragment());
    }

    public void reset() {
        if (passwordIsValidate()) {
            Call<ResponseBody> reset = RetrofitClient.resetPassword(authActivity.getSharedEmail(), binding.password.getText().toString());
            reset.enqueue(new CallBack());
        } else {
            AlertDialog dialog = new AlertDialog(authActivity, EMPTY, authActivity.getString(R.string.check_the_form));
            dialog.show();
        }
    }

    private boolean passwordIsValidate() {
        boolean isValidate = true;
        String password = binding.password.getText().toString();
        String confirmPassword = binding.confirmPassword.getText().toString();

        if (password.length() < 6) {
            isValidate = false;
            binding.password.setError(authActivity.getString(R.string.password_must));
        }

        if (password.contains(SINGLE_QUOTE)) {
            isValidate = false;
            binding.password.setError(authActivity.getString(R.string.error_character));
        }

        if (!password.contains(confirmPassword)) {
            isValidate = false;
            binding.confirmPassword.setError(authActivity.getString(R.string.password_not_match));
        }

        return isValidate;
    }

    class CallBack implements Callback<ResponseBody> {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                try {
                    String result = response.body().source().readUtf8();
                    if (result.contains(SUCCESS)) {
                        JSONObject data = new JSONObject(result);
                        DatabaseHelper db = new DatabaseHelper(authActivity);
                        db.createUserByObject(data);
                        Intent intent = new Intent(authActivity, MainActivity.class);
                        authActivity.startActivity(intent);
                        authActivity.finish();
                    } else {
                        AlertDialog dialog = new AlertDialog(authActivity, EMPTY, result);
                        dialog.show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    AlertDialog dialog = new AlertDialog(authActivity, EMPTY, e.getMessage());
                    dialog.show();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            AlertDialog dialog = new AlertDialog(authActivity, EMPTY, t.getMessage());
            dialog.show();
        }
    }
}
