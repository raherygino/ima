package com.gsoft.ima.ui.signup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.gsoft.ima.databinding.ActivitySignupBinding;
import com.gsoft.ima.ui.login.LoginActivity;
import com.gsoft.ima.utils.eventlistener.DateSet;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    SignupActivityViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new SignupActivityViewModel(this);
        binding.setViewModel(viewModel);
        binding.birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(SignupActivity.this, new DateSet(viewModel.birthday), calendar.get(Calendar.YEAR)-70, 0, 1);
                dialog.setCancelable(true);
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
