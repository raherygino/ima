package com.gsoft.ima.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentHomeBinding;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.models.Transaction;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new HomeViewModel(getContext());
        binding.logout.setOnClickListener(new OnClick());
        Transaction transaction = new Transaction(0,"",0,"");
        transaction.nameSender = "Georginot";
        transaction.nameReceiver = "Armelin";
        transaction.date = "12 Déc 2022";
        transaction.sentBy = "Bluetooth";
        transaction.numReceiver = "034 65 007 00";
        Toast.makeText(getContext(), transaction.nameReceiver, Toast.LENGTH_LONG).show();
        return binding.getRoot();
    }

    class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();

            if (id == R.id.logout) {
                AlertDialog dialog = new AlertDialog(getContext(),"Logout","You want to logout?");
                dialog.btnOk.setOnClickListener(this);
                dialog.show();
            } else if (id == R.id.btn_ok) {
                viewModel.logout();
            }
        }
    }
}
