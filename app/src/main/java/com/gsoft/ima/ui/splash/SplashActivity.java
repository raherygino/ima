package com.gsoft.ima.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gsoft.ima.databinding.ActivitySplashBinding;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Utils.setColorBarStatus(this);

        DatabaseHelper database = new DatabaseHelper(this);
        if (database.getAllDistrict().getCount() == 0) {
            Utils.importDataFromAssets(this);
        }
        Thread splash=new Thread() {
            public void run() {
                try{
                    sleep(3*1000);
                    if (database.isLogged()) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, AuthActivity.class));
                    }
                    finish();
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }
        };
        splash.start();
    }
}
