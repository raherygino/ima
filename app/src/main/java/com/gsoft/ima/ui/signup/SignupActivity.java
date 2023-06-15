package com.gsoft.ima.ui.signup;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.ActivitySignupBinding;
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

        binding.gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(SignupActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.gender, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        EditText editText = (EditText) view;
                        editText.setText(menuItem.getTitle());
                        if (menuItem.getItemId() == R.id.male) {
                            viewModel.gender.set("male");
                        } else {
                            viewModel.gender.set("female");
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
