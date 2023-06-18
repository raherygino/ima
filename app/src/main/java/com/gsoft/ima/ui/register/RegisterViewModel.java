package com.gsoft.ima.ui.register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.gsoft.ima.R;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.databinding.FragmentRegisterBinding;
import com.gsoft.ima.di.dialog.WebViewDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.login.LoginFragment;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.utils.DateSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private Context context;
    private FragmentRegisterBinding binding;

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

    public  RegisterViewModel(RegisterFragment fragment) {
        this.context = fragment.getContext();
        this.binding = fragment.binding;
    }

    public void setChangeBirthDay() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, new DateSet(birthday, binding.birthDate), calendar.get(Calendar.YEAR)-40, 0, 1);
        dialog.setCancelable(true);
        dialog.show();
        Toast.makeText(context, lastname.get(), Toast.LENGTH_SHORT).show();
    }

    public void setChooseGender() {
        PopupMenu popupMenu = new PopupMenu(context, binding.gender);
        popupMenu.getMenuInflater().inflate(R.menu.gender, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                binding.gender.setText(menuItem.getTitle());
                if (menuItem.getItemId() == R.id.male) {
                    gender.set("male");
                } else {
                    gender.set("female");
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void loginListener() {
        AuthActivity authActivity = (AuthActivity) context;
        authActivity.setFragment(new LoginFragment());
    }

    public void registerListener() {
        lastname.set(binding.lastname.getText().toString());
        firstname.set(binding.firstname.getText().toString());
        birthplace.set(binding.birthPlace.getText().toString());
        id_card.set(binding.cin.getText().toString());
        country.set(binding.country.getText().toString());
        city.set(binding.city.getText().toString());
        phone.set(binding.phone.getText().toString());
        email.set(binding.email.getText().toString());
        password.set(binding.password.getText().toString());
        confirmPassword.set(binding.confirmPassword.getText().toString());

        User user = new User(
                lastname.get(), firstname.get(),
                gender.get(), birthday.get(), birthplace.get(),
                id_card.get(), country.get(), city.get(), phone.get(),
                email.get(),password.get(), confirmPassword.get());

        if (user.isValidate(context, binding) && binding.acceptSignup.isChecked()) {
            register(user);
        } else {
            Snackbar.make(binding.getRoot(), context.getString(R.string.check_the_form), Snackbar.LENGTH_LONG).show();
        }
    }

    private void register(User user) {

        Call<ResponseBody> createUser = RetrofitClient.createUser(user);
        createUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().source().readUtf8();
                        DatabaseHelper db = new DatabaseHelper(context);
                        if (result.contains("created")) {
                            if (db.createUser(user)!= -1) {
                                Activity activity = (Activity) context;
                                activity.startActivity(new Intent(context, MainActivity.class));
                                activity.finish();
                            }
                        } else {
                            WebViewDialog dialog = new WebViewDialog(context, "Result", response.body().source().readUtf8());
                            dialog.show();
                        }
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
    }
}
