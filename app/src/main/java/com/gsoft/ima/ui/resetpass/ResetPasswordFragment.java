package com.gsoft.ima.ui.resetpass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentResetPassBinding;
import com.gsoft.ima.di.components.EditText;

public class ResetPasswordFragment extends Fragment {
    private FragmentResetPassBinding binding;
    private ResetPasswordViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResetPassBinding.inflate(getLayoutInflater());
        viewModel = new ResetPasswordViewModel(getContext(), binding);
        binding.btnCancel.setOnClickListener(new onClick());
        binding.btnReset.setOnClickListener(new onClick());
        binding.showPassword.setOnClickListener(new onClick());
        return binding.getRoot();
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btn_cancel:
                    viewModel.cancel();
                    break;

                case R.id.btn_reset:
                    viewModel.reset();
                    break;

                case R.id.show_password:
                    viewModel.togglePassword(((CheckBox) view).isChecked());
                    break;
            }
        }
    }
}
