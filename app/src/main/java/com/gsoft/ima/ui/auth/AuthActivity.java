package com.gsoft.ima.ui.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivityAuthBinding;
import com.gsoft.ima.ui.login.LoginFragment;
import com.gsoft.ima.ui.signup.SignupFragment;

public class AuthActivity extends AppCompatActivity {
    private ActivityAuthBinding binding;
    private String currentFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFragment(new LoginFragment());
    }

    public void setFragment(Fragment fragment) {
        currentFragment =  fragment.getClass().toString().replace("class", "");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_auth_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment.contains("SignupFragment")) {
            setFragment(new LoginFragment());
        } else {
            super.onBackPressed();
        }
    }
}
