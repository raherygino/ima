package com.gsoft.ima.ui.forgot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentForgotPassBinding;
import com.gsoft.ima.di.dialog.AlertDialog;

public class ForgotFragment extends Fragment {
    ForgotViewModel viewModel;
    FragmentForgotPassBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPassBinding.inflate(getLayoutInflater());
        viewModel = new ForgotViewModel(getContext(), binding);

        binding.btnSend.setOnClickListener(new onClick());
        binding.btnReset.setOnClickListener(new onClick());

        return binding.getRoot();
    }

    class onClick implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btn_send:
                    viewModel.send(binding.email.getText().toString());
                    break;
                case R.id.btn_reset:
                    viewModel.reset(binding.email.getText().toString(), binding.resetCode.getText().toString());
                    break;
            }
        }
    }
}
