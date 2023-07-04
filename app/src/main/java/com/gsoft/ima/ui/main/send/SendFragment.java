package com.gsoft.ima.ui.main.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentSendBinding;
import com.gsoft.ima.utils.Utils;


public class SendFragment extends Fragment {
    private  SendViewModel viewModel;
    private  FragmentSendBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSendBinding.inflate(getLayoutInflater());
        Utils.setColorBarStatusDefault(getContext());
        viewModel = new SendViewModel(getContext(), binding);
        Utils.setOnHoverLabel(binding.sendBy);
        binding.sendBy.setOnClickListener(new onClick());
        binding.btnSend.setOnClickListener(new onClick());
        return binding.getRoot();
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.send_by) {
                viewModel.onChangeType();
            } else if (id == R.id.btn_send) {
                viewModel.send();
            }
        }
    }
}
