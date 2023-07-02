package com.gsoft.ima.ui.register;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentRegisterBinding;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.di.components.Label;
import com.gsoft.ima.utils.Utils;

import static com.gsoft.ima.constants.main.MainConstants.SLASH;

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
        this.binding.cin.addTextChangedListener(new Utils.CountValidation(getContext(), binding.countCin));
        Utils.setOnHoverLabel(binding.birthDate);
        Utils.setOnHoverLabel(binding.gender);
        Utils.setOnHoverLabel(binding.country);
        this.viewModel = new RegisterViewModel(this);
        return binding.getRoot();
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

