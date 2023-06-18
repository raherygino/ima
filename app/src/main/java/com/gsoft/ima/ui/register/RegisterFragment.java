package com.gsoft.ima.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentRegisterBinding;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.di.components.Label;

public class RegisterFragment extends Fragment {
    public FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentRegisterBinding.inflate(inflater, container, false);
        this.binding.birthDate.setOnClickListener(new onClick());
        this.binding.gender.setOnClickListener(new onClick());
        this.binding.btnLogin.setOnClickListener(new onClick());
        this.binding.btnRegister.setOnClickListener(new onClick());
        this.setOnHoverLabel(binding.birthDate);
        this.setOnHoverLabel(binding.gender);
        this.setOnHoverLabel(binding.country);
        this.viewModel = new RegisterViewModel(this);
        return binding.getRoot();
    }

    private void setOnHoverLabel(EditText editText) {
        Label layoutLabel = (Label) editText.getParent();
        TextView label = (TextView) layoutLabel.getChildAt(0);
        label.animate().translationY(-8).setDuration(100).start();
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.birth_date) {
                viewModel.setChangeBirthDay();
            } else if (id == R.id.gender) {
                viewModel.setChooseGender();
            } else if (id == R.id.btn_login) {
                viewModel.loginListener();
            } else if (id == R.id.btn_register) {
                viewModel.registerListener();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

