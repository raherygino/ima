package com.gsoft.ima.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentLoginBinding;
import com.gsoft.ima.di.dialog.PromptDialog;

public class LoginFragment extends Fragment {
    public FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewModel = new LoginViewModel(this);
        this.setOnClickListener();
        return binding.getRoot();
    }

    private void setOnClickListener() {
        binding.forgotPass.setOnClickListener(new onClick());
        binding.btnLogin.setOnClickListener(new onClick());
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.btn_login) {
                viewModel.loginListener();
            } else if (id == R.id.forgot_pass) {
                PromptDialog dialog = new PromptDialog(
                        getContext(),
                        getString(R.string.forgot),
                        getString(R.string.forgot_pass_info),
                        getString(R.string.email));
                dialog.show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
