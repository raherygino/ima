package com.gsoft.ima.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentManager;

import com.gsoft.ima.R;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.databinding.FragmentLoginBinding;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.di.dialog.PromptDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.forgot.ForgotFragment;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.ui.register.RegisterFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gsoft.ima.constants.main.FormConstants.*;
import static com.gsoft.ima.constants.main.MainConstants.*;

public class LoginViewModel extends BaseObservable {

    private final Context context;
    private final AuthActivity activity;
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    private FragmentLoginBinding binding;

    public LoginViewModel(LoginFragment fragment) {
        this.context = fragment.getContext();
        this.activity = (AuthActivity) context;
        this.binding = fragment.binding;
    }

    public void loginListener() {
        phone.set(binding.phone.getText().toString());
        password.set(binding.password.getText().toString());

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.show();

        Call<ResponseBody> createUser = RetrofitClient.login(phone.get(), password.get());
        createUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.hide();
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        String result = response.body().source().readUtf8();
                        if (result.contains(CREATED_AT)) {
                            try {
                                JSONObject data = new JSONObject(result);
                                DatabaseHelper db = new DatabaseHelper(context);
                                db.createUserByObject(data);
                                Intent intent = new Intent(context, MainActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                                AlertDialog dialog = new AlertDialog(context, EMPTY, jsonException.getMessage());
                                dialog.show();
                            }
                        } else {
                            AlertDialog dialog = new AlertDialog(context, EMPTY, context.getString(R.string.error_login));
                            dialog.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        AlertDialog dialog = new AlertDialog(context, EMPTY, e.getMessage());
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
        });
    }

    public void registerListener() {
        activity.setFragment(new RegisterFragment());
    }

    public void forgotPassListener() {
        activity.setFragment(new ForgotFragment());
    }
}
