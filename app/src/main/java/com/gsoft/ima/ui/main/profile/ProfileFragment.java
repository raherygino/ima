package com.gsoft.ima.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.databinding.FragmentProfileBinding;
import com.gsoft.ima.utils.Utils;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        Utils.setColorBarStatusDefault(getContext());
        ProfileViewModel viewModel = new ProfileViewModel(getContext());

        binding.titleUsername.setText(viewModel.user.firstname);
        binding.titleEmail.setText(viewModel.user.email);
        binding.firstname.setText(viewModel.user.firstname);
        binding.lastname.setText(viewModel.user.lastname);
        binding.gender.setText(viewModel.user.gender);
        binding.birthDate.setText(viewModel.user.birthday);
        binding.birthPlace.setText(viewModel.user.birthplace);
        binding.cin.setText(viewModel.user.id_card);
        binding.country.setText(viewModel.user.country);
        binding.city.setText(viewModel.user.city);
        binding.phone.setText(viewModel.user.phone);
        binding.email.setText(viewModel.user.email);
        binding.password.setText(viewModel.user.password);

        Utils.setOnHoverLabel(binding.firstname);
        Utils.setOnHoverLabel(binding.lastname);
        Utils.setOnHoverLabel(binding.gender);
        Utils.setOnHoverLabel(binding.birthDate);
        Utils.setOnHoverLabel(binding.birthPlace);
        Utils.setOnHoverLabel(binding.cin);
        Utils.setOnHoverLabel(binding.country);
        Utils.setOnHoverLabel(binding.city);
        Utils.setOnHoverLabel(binding.phone);
        Utils.setOnHoverLabel(binding.email);
        Utils.setOnHoverLabel(binding.password);

        return binding.getRoot();
    }
}
