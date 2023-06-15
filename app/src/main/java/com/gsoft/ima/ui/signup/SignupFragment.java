package com.gsoft.ima.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.databinding.FragementSignupBinding;
import com.gsoft.ima.ui.login.LoginViewModel;

public class SignupFragment extends Fragment {
    FragementSignupBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragementSignupBinding.inflate(inflater, container, false);
        SignupViewModel viewModel = new SignupViewModel(getContext());
        View root = binding.getRoot();
        binding.setViewModel(viewModel);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
