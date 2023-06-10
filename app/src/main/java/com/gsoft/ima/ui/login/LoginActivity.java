package com.gsoft.ima.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LoginActivityViewModel viewModel = new LoginActivityViewModel(this);
        binding.setViewModel(viewModel);
        viewModel.phone.set("0346500700");
        viewModel.password.set("1234567890");
    }
}
