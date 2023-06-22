package com.gsoft.ima.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivityMainBinding;
import com.gsoft.ima.di.components.BottomNav;
import com.gsoft.ima.ui.main.home.HomeFragment;
import com.gsoft.ima.utils.Utils;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private String currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Utils.setColorBarStatus(this);
        setFragment(new HomeFragment());
        BottomNav nav = new BottomNav(this);
        nav.setConfig();
        nav.setItemActivate(0);
    }

    public void setFragment(Fragment fragment) {
        currentFragment =  fragment.getClass().toString().replace("class", "");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, fragment);
        fragmentTransaction.commit();
    }
}
