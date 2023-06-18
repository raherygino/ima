package com.gsoft.ima.ui.register;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentRegisterBinding;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.login.LoginFragment;
import com.gsoft.ima.utils.DateSet;

import java.util.Calendar;

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
}
