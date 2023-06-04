package com.gsoft.ima.view.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.gsoft.ima.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}