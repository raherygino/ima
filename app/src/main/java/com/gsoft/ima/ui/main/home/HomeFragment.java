package com.gsoft.ima.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentHomeBinding;
import com.gsoft.ima.di.dialog.ConfirmDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.ui.main.home.adapter.HomeAdapterHistory;
import com.gsoft.ima.ui.main.home.adapter.HomeAdapterMenu;
import com.gsoft.ima.utils.Utils;
import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel viewModel;
    HomeAdapterHistory mAdapter;
    ArrayList<Transaction> transactions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new HomeViewModel(getContext());
        binding.username.setText(viewModel.user.firstname);
        binding.logout.setOnClickListener(new OnClick());
        Utils.setColorBarStatus(getContext());
        configTransactions();

        return binding.getRoot();
    }

    private void configTransactions() {
        transactions = new DatabaseHelper(getContext()).getAllTransaction();
        mAdapter = new HomeAdapterHistory(getContext(), transactions);
        binding.listView.setAdapter(mAdapter);
        binding.listView.setMenuCreator(new HomeAdapterMenu(getContext()));
        binding.listView.setOnMenuItemClickListener(new HomeAdapterMenu.onItemClicked(getContext(), transactions, mAdapter));
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
