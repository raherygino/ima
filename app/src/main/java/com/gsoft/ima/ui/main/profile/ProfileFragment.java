package com.gsoft.ima.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.databinding.FragmentProfileBinding;
import com.gsoft.ima.model.models.UserData;
import com.gsoft.ima.utils.Utils;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        Utils.setColorBarStatusDefault(getContext());
        ProfileViewModel viewModel = new ProfileViewModel(getContext());
        UserData userData = new UserData();
        userData.setToView(binding, viewModel.user);
        return binding.getRoot();
    }
}
