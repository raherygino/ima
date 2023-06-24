package com.gsoft.ima.ui.main.scan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.databinding.FragmentScanBinding;
import com.gsoft.ima.utils.Utils;

public class ScanFragment extends Fragment {

    FragmentScanBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        Utils.setColorBarStatusDefault(getContext());
        return binding.getRoot();
    }
}
