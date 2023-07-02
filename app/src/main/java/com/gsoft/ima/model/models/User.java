package com.gsoft.ima.model.models;

import android.content.Context;
import android.util.Patterns;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentRegisterBinding;

public class User {
    public String lastname;
    public String firstname;
    public String gender;
    public String birthday;
    public String birthplace;
    public String id_card;
    public String country;
    public String city;
    public String phone;
    public String email;
    public String password;
    public String confirmPassword;
    public String token;

    public User(
            String firstname,
            String lastname,
            String gender,
            String birthday,
            String birthplace,
            String id_card,
            String country,
            String city,
            String phone,
            String email,
            String password,
            String confirmPassword) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.birthday = birthday;
        this.birthplace = birthplace;
        this.id_card = id_card;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public boolean isValidate(Context context, FragmentRegisterBinding binding) {
        boolean isValidate = true;

        if (this.lastname.length() < 3) {
            isValidate = false;
            binding.lastname.setError(context.getString(R.string.value_too_short));
        }

        if (this.birthplace.isEmpty()) {
            isValidate = false;
            binding.birthPlace.setError(context.getString(R.string.edit_text_required));
        }

        if (this.birthplace.length() < 3) {
            isValidate = false;
            binding.birthPlace.setError(context.getString(R.string.value_too_short));
        }

        if (this.id_card.length() != 12){
            isValidate = false;
            binding.cin.setError(context.getString(R.string.cin_invalid));
        }

        if (this.city.length() < 3) {
            isValidate = false;
            binding.city.setError(context.getString(R.string.value_too_short));
        }

        if (this.phone.length() != 10){
            isValidate = false;
            binding.phone.setError(context.getString(R.string.phone_invalid));
        }

        if (this.email.isEmpty()) {
            isValidate = false;
            binding.email.setError(context.getString(R.string.edit_text_required));
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(this.email).matches()) {
            isValidate = false;
            binding.email.setError(context.getString(R.string.email_invalid));
        }

        if (this.password.length() < 6) {
            isValidate = false;
            binding.password.setError(context.getString(R.string.password_must));
        }

        if (!this.password.contains(this.confirmPassword)) {
            isValidate = false;
            binding.confirmPassword.setError(context.getString(R.string.password_not_match));
        }

        return isValidate;
    }
}