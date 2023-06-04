package com.gsoft.ima.view.activities;

import static com.gsoft.ima.constants.network.NetworkConstants.LOCATION_PERMISSION_REQUEST_CODE;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.material.snackbar.Snackbar;
import com.gsoft.ima.databinding.ActivityMainBinding;
import com.gsoft.ima.view.adapters.VenueAdapter;
import com.gsoft.ima.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new MainViewModel(this);
        binding.setViewModel(viewModel);
        ListView listView = binding.listView;
        listView.setAdapter(new VenueAdapter(this, viewModel.getVenues()));
        viewModel.setupAutoCompleteTextView(binding.inputSearch, this);
        binding.setLifecycleOwner(this);
        viewModel.snackbarMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (!message.isEmpty()) {
                    Snackbar.make( binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getMyLocation();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getMyLocation();
    }
}