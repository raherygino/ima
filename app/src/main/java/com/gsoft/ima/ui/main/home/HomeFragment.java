package com.gsoft.ima.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentHomeBinding;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.di.dialog.ConfirmDialog;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.utils.Utils;

import java.util.ArrayList;

import static com.gsoft.ima.constants.main.MainConstants.*;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new HomeViewModel(getContext());
        binding.username.setText(viewModel.user.firstname);

        binding.logout.setOnClickListener(new OnClick());
        Utils.setColorBarStatus(getContext());
        setRecycleViewHistory();
        return binding.getRoot();
    }

    private void setRecycleViewHistory() {
        ArrayList<Transaction> listTransaction = seedTransaction(3, KEY_RECEIVED);
        listTransaction.addAll(seedTransaction(3, KEY_SENT));
        binding.recyclerViewHistory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTransaction = new LinearLayoutManager(getContext());
        binding.recyclerViewHistory.setLayoutManager(layoutManagerTransaction);
        HomeAdapterRecyclerTransaction adapterRecyclerTransaction = new HomeAdapterRecyclerTransaction(getContext(), listTransaction, binding.recyclerViewHistory);
        binding.recyclerViewHistory.setAdapter(adapterRecyclerTransaction);
    }

    private ArrayList<Transaction> seedTransaction(int size,String type) {
        ArrayList<Transaction> listTransaction = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Transaction transaction = new Transaction(0,"",0,"");
            transaction.nameSender = "Georginot";
            transaction.nameReceiver = "Armelin";
            transaction.date = "12 Déc 2022";
            transaction.sentBy = "Bluetooth";
            transaction.numReceiver = "034 65 007 00";
            transaction.amount = 8000*(i+1);
            transaction.type = type;
            listTransaction.add(transaction);
        }
        return listTransaction;
    }

    class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();

            if (id == R.id.logout) {
                ConfirmDialog dialog = new ConfirmDialog(getContext(),getString(R.string.logout),getString(R.string.logout_details));
                dialog.btnOk.setOnClickListener(this);
                dialog.show();
            } else if (id == R.id.btn_ok) {
                viewModel.logout();
            }
        }
    }
}
