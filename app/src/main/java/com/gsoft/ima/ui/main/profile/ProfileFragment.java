package com.gsoft.ima.ui.main.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentProfileBinding;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.model.models.UserData;
import com.gsoft.ima.utils.Utils;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private UserData userData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        Utils.setColorBarStatusDefault(getContext());
        viewModel = new ProfileViewModel(getContext());
        userData = new UserData();
        userData.setToView(getContext(), binding, viewModel.user);
        binding.btnUpdate.setOnClickListener(new OnClick());
        binding.birthDate.setOnClickListener(new OnClick());
        binding.gender.setOnClickListener(new OnClick());
        return binding.getRoot();
    }

    class OnClick implements View.OnClickListener{
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btn_update:
                    viewModel.updateData(userData.getFromView(binding));
                    userData.setToView(getContext(), binding, viewModel.user);
                    break;

                case R.id.birth_date:
                    viewModel.setChangeBirthDay(userData, (EditText) view);
                    break;

                case R.id.gender:
                    viewModel.setChooseGender(userData, (EditText) view);
                    break;
            }
        }
    }
}
