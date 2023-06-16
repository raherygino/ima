package com.gsoft.ima.ui.signup;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentSignupBinding;
import com.gsoft.ima.utils.eventlistener.DateSet;

import java.util.Calendar;

public class SignupFragment extends Fragment {
    FragmentSignupBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        SignupViewModel viewModel = new SignupViewModel(getContext());
        View root = binding.getRoot();
        binding.setViewModel(viewModel);

        binding.birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DateSet(viewModel.birthday), calendar.get(Calendar.YEAR)-40, 0, 1);
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        binding.gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
