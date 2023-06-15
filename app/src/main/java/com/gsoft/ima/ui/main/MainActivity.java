package com.gsoft.ima.ui.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.gsoft.ima.databinding.ActivityMainBinding;
import com.gsoft.ima.ui.auth.AuthActivity;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
         startActivity(new Intent(MainActivity.this, AuthActivity.class));
         finish();
    }
}