package com.gsoft.ima.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivityAuthBinding;
import com.gsoft.ima.di.dialog.ConfirmDialog;
import com.gsoft.ima.ui.auth.forgot.ForgotFragment;
import com.gsoft.ima.ui.auth.login.LoginFragment;

import static com.gsoft.ima.constants.main.FormConstants.EMAIL;
import static com.gsoft.ima.constants.main.MainConstants.*;

public class AuthActivity extends AppCompatActivity {
    ActivityAuthBinding binding;
    private String currentFragment;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFragment(new LoginFragment());
    }

    public void setSharedEmail(String email) {
        this.sharedPreferences = getSharedPreferences(EMAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public String getSharedEmail() {
        return sharedPreferences.getString(EMAIL, "No Data");
    }

    public void setFragment(Fragment fragment) {
        currentFragment =  fragment.getClass().toString().replace(CLASS, EMPTY);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_auth_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (!currentFragment.contains(FRG_LOGIN)) {
            if (currentFragment.contains(FRG_RESET)) {
                setFragment(new ForgotFragment());
            } else {
                setFragment(new LoginFragment());
            }
        } else {
            ConfirmDialog dialog = new ConfirmDialog(this, getString(R.string.close_app), getString(R.string.close_app_details));
            dialog.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            dialog.show();
        }
    }
}
