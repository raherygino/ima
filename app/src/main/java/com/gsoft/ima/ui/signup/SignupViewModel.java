package com.gsoft.ima.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.models.User;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.login.LoginFragment;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupViewModel extends BaseObservable {

    private final Activity activity;
    private final Context context;

    public ObservableField<String> lastname = new ObservableField<>("");
    public ObservableField<String> firstname = new ObservableField<>("");
    public ObservableField<String> gender = new ObservableField<>("");
    public ObservableField<String> birthday = new ObservableField<>("01-01-1980");
    public ObservableField<String> birthplace = new ObservableField<>("");
    public ObservableField<String> id_card = new ObservableField<>("");
    public ObservableField<String> country = new ObservableField<>("");
    public ObservableField<String> city = new ObservableField<>("");
    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> confirmPassword = new ObservableField<>("");



    public SignupViewModel(Context context) {
        this.activity = (Activity) context;
        this.context = context;
    }

    public void signupListener() {

        User user = new User(
                lastname.get(),
                firstname.get(),
                gender.get(),
                birthday.get(),
                birthplace.get(),
                id_card.get(),
                country.get(),
                city.get(),
                phone.get(),
                email.get(),
                password.get()
        );

        if (user.isValidate()) {
            Call<ResponseBody> createUser = RetrofitClient.createUser(user);
            createUser.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            Toast.makeText(context, response.body().source().readUtf8(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "Field required", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginListener() {
        AuthActivity authActivity = (AuthActivity) activity;
        authActivity.setFragment(new LoginFragment());
    }
}
