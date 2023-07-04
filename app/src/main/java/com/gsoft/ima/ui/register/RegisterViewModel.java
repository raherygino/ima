package com.gsoft.ima.ui.register;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.gsoft.ima.R;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.databinding.FragmentRegisterBinding;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.di.dialog.ListDialog;
import com.gsoft.ima.di.dialog.WebViewDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.model.models.UserData;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.login.LoginFragment;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.utils.Utils;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gsoft.ima.constants.main.MainConstants.*;

public class RegisterViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private FragmentRegisterBinding binding;
    private UserData userData;

    public  RegisterViewModel(RegisterFragment fragment) {
        this.context = fragment.getContext();
        this.binding = fragment.binding;
        this.userData = new UserData();
    }

    public void setChangeBirthDay() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, new Utils.DateSet(userData.birthday, binding.birthDate), calendar.get(Calendar.YEAR)-40, 0, 1);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void togglePassword(boolean isShow) {
        if (isShow) {
            binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void setChooseGender() {
        PopupMenu popupMenu = new PopupMenu(context, binding.gender);
        popupMenu.getMenuInflater().inflate(R.menu.gender, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                binding.gender.setText(menuItem.getTitle());
                if (menuItem.getItemId() == R.id.male) {
                    userData.gender.set(MALE);
                } else {
                    userData.gender.set(FEMALE);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void setChooseCity(EditText editText) {
        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.getAllDistrict();
        String[] items = new String[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            items[i] = cursor.getString(1);
        }

        String title = context.getString(R.string.city);

        ListDialog dialog = new ListDialog(context,
                context.getString(R.string.list_of, title),
                items,
                editText);

        dialog.show();
    }

    public void loginListener() {
        AuthActivity authActivity = (AuthActivity) context;
        authActivity.setFragment(new LoginFragment());
    }

    public void registerListener() {
        User user = userData.getFromView(binding);
        user.balance = 45000;
        if (user.isValidate(context, binding) && binding.acceptSignup.isChecked()) {
            register(user);
        } else {
            Snackbar.make(binding.getRoot(), context.getString(R.string.check_the_form), Snackbar.LENGTH_LONG).show();
        }
    }

    private void register(User user) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.show();

        Call<ResponseBody> createUser = RetrofitClient.createUser(user);
        createUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.hide();
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        String result = response.body().source().readUtf8();
                        boolean isEmailExist = result.contains(IS_EMAIL_EXIST);
                        boolean isPhoneExist = result.contains(IS_PHONE_EXIST);
                        if (result.contains(IS_CREATED)) {
                            createUserInLocalDB(user);
                        } else {
                            if (isEmailExist || isPhoneExist) {
                                AlertDialog dialog;
                                String title, details;
                                if (isEmailExist) {
                                    title = context.getString(R.string.error_email);
                                    details = context.getString(R.string.error_email_details, user.email);
                                    binding.email.setError(title);
                                } else {
                                    title = context.getString(R.string.error_phone);
                                    details = context.getString(R.string.error_phone_details, user.phone);
                                    binding.phone.setError(title);
                                }
                                dialog = new AlertDialog(context, title, details);
                                dialog.show();
                            } else {
                                AlertDialog dialog = new AlertDialog(context, context.getString(R.string.error), EMPTY);
                                dialog.show();
                            }
                        }
                    } catch (IOException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        Toast.makeText(context, "error: "+response.errorBody().source().readUtf8(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
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

    private void createUserInLocalDB(User user) {
        DatabaseHelper db = new DatabaseHelper(context);
        if (db.createUser(user)!= -1) {
            Activity activity = (Activity) context;
            activity.startActivity(new Intent(context, MainActivity.class));
            activity.finish();
        }
    }
}
