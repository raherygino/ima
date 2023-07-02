package com.gsoft.ima.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivityMainBinding;
import com.gsoft.ima.di.components.BottomNav;
import com.gsoft.ima.di.dialog.SendDialog;
import com.gsoft.ima.ui.main.home.HomeFragment;
import com.gsoft.ima.utils.Utils;

import static com.gsoft.ima.constants.main.MainConstants.*;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String currentFragment;
    private BottomNav nav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Utils.setColorBarStatus(this);
        setFragment(new HomeFragment());
        nav = new BottomNav(this);
        nav.setConfig();
        nav.setItemActivate(0);
        binding.fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDialog dialog = new SendDialog(MainActivity.this);
                dialog.show();
            }
        });
    }

    public void setFragment(Fragment fragment) {
        currentFragment =  fragment.getClass().toString().replace(CLASS, EMPTY);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (!currentFragment.contains(FRG_HOME)) {
            setFragment(new HomeFragment());
            nav.setItemActivate(0);
        } else {
            super.onBackPressed();
        }
    }
}
