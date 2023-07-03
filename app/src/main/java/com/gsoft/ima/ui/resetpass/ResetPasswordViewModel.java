package com.gsoft.ima.ui.resetpass;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.databinding.FragmentResetPassBinding;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.forgot.ForgotFragment;

public class ResetPasswordViewModel extends ViewModel {
    FragmentResetPassBinding binding;
    @SuppressLint("StaticFieldLeak")
    AuthActivity authActivity;

    public ResetPasswordViewModel(Context context, FragmentResetPassBinding binding) {
        this.authActivity = (AuthActivity) context;
        this.binding = binding;
    }

    public void cancel() {
        authActivity.setFragment(new ForgotFragment());
    }

    public void reset() {

    }
}
