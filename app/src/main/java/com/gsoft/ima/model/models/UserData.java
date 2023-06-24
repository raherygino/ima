package com.gsoft.ima.model.models;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentProfileBinding;
import com.gsoft.ima.databinding.FragmentRegisterBinding;
import com.gsoft.ima.utils.Utils;

public class UserData {

    public ObservableField<String> lastname = new ObservableField<>("");
    public ObservableField<String> firstname = new ObservableField<>("");
    public ObservableField<String> gender = new ObservableField<>("male");
    public ObservableField<String> birthday = new ObservableField<>("01-01-1980");
    public ObservableField<String> birthplace = new ObservableField<>("");
    public ObservableField<String> id_card = new ObservableField<>("");
    public ObservableField<String> country = new ObservableField<>("");
    public ObservableField<String> city = new ObservableField<>("");
    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> confirmPassword = new ObservableField<>("");

    public User getFromView(FragmentRegisterBinding binding) {
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
        return setUser();
    }

    public User getFromView(FragmentProfileBinding binding) {
        lastname.set(binding.lastname.getText().toString());
        firstname.set(binding.firstname.getText().toString());
        birthday.set(binding.birthDate.getText().toString());
        birthplace.set(binding.birthPlace.getText().toString());
        id_card.set(binding.cin.getText().toString());
        country.set(binding.country.getText().toString());
        city.set(binding.city.getText().toString());
        phone.set(binding.phone.getText().toString());
        email.set(binding.email.getText().toString());
        password.set(binding.password.getText().toString());
        return setUser();
    }
    
    public void setToView(Context context, FragmentProfileBinding binding, User user) {

        binding.titleUsername.setText(user.firstname);
        binding.titleEmail.setText(user.email);
        binding.firstname.setText(user.firstname);
        binding.lastname.setText(user.lastname);

        if (user.gender.equals("male")) {
            binding.gender.setText(context.getString(R.string.male));
        } else {
            binding.gender.setText(context.getString(R.string.female));
        }

        binding.birthDate.setText(user.birthday);
        binding.birthPlace.setText(user.birthplace);
        binding.cin.setText(user.id_card);
        binding.country.setText(user.country);
        binding.city.setText(user.city);
        binding.phone.setText(user.phone);
        binding.email.setText(user.email);
        binding.password.setText(user.password);

        Utils.setOnHoverLabel(binding.firstname);
        Utils.setOnHoverLabel(binding.lastname);
        Utils.setOnHoverLabel(binding.gender);
        Utils.setOnHoverLabel(binding.birthDate);
        Utils.setOnHoverLabel(binding.birthPlace);
        Utils.setOnHoverLabel(binding.cin);
        Utils.setOnHoverLabel(binding.country);
        Utils.setOnHoverLabel(binding.city);
        Utils.setOnHoverLabel(binding.phone);
        Utils.setOnHoverLabel(binding.email);
        Utils.setOnHoverLabel(binding.password);
    }
    
    private User setUser() {

        User user = new User(
                lastname.get(), firstname.get(),
                gender.get(), birthday.get(), birthplace.get(),
                id_card.get(), country.get(), city.get(), phone.get(),
                email.get(),password.get(), "");

        return user;
        
    }
}
