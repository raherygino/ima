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
import com.gsoft.ima.di.dialog.ConfirmDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
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
        ArrayList<Transaction> listTransaction = new DatabaseHelper(getContext()).getAllTransaction();
        binding.recyclerViewHistory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTransaction = new LinearLayoutManager(getContext());
        binding.recyclerViewHistory.setLayoutManager(layoutManagerTransaction);
        HomeAdapterRecyclerTransaction adapterRecyclerTransaction = new HomeAdapterRecyclerTransaction(getContext(), listTransaction, binding.recyclerViewHistory);
        binding.recyclerViewHistory.setAdapter(adapterRecyclerTransaction);
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
