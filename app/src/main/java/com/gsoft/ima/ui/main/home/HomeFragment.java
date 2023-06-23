package com.gsoft.ima.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentHomeBinding;
import com.gsoft.ima.di.adapters.AdapterRecyclerTransaction;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.models.Transaction;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new HomeViewModel(getContext());
        binding.logout.setOnClickListener(new OnClick());

        ArrayList<Transaction> listTransaction = seedTransaction(3, "received");
        listTransaction.addAll(seedTransaction(3, "sent"));
        binding.recyclerViewHistory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTransaction = new LinearLayoutManager(getContext());
        binding.recyclerViewHistory.setLayoutManager(layoutManagerTransaction);
        AdapterRecyclerTransaction adapterRecyclerTransaction = new AdapterRecyclerTransaction(getContext(), listTransaction, binding.recyclerViewHistory);
        binding.recyclerViewHistory.setAdapter(adapterRecyclerTransaction);

        return binding.getRoot();
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
                AlertDialog dialog = new AlertDialog(getContext(),getString(R.string.logout),getString(R.string.logout_details));
                dialog.btnOk.setOnClickListener(this);
                dialog.show();
            } else if (id == R.id.btn_ok) {
                viewModel.logout();
            }
        }
    }
}
