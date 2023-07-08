package com.gsoft.ima.ui.main.home;

import static com.gsoft.ima.constants.main.MainConstants.FORMAT_NUMBER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentHomeBinding;
import com.gsoft.ima.di.dialog.ConfirmDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.ui.main.home.adapter.HomeAdapterHistory;
import com.gsoft.ima.ui.main.home.adapter.HomeAdapterMenu;
import com.gsoft.ima.utils.UserLogged;
import com.gsoft.ima.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel viewModel;
    HomeAdapterHistory mAdapter;
    ArrayList<Transaction> transactions;
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new HomeViewModel(getContext(), binding);
        user = UserLogged.data(getContext());
        DecimalFormat decimalFormat = new DecimalFormat(FORMAT_NUMBER);
        binding.imageViewUser.setOnClickListener(new OnClick());
        binding.username.setText(viewModel.user.firstname);
        binding.phoneNumber.setText(viewModel.user.phone);
        binding.balance.setText(decimalFormat.format(user.balance));
        binding.logout.setOnClickListener(new OnClick());
        binding.refresh.setOnClickListener(new OnClick());
        binding.countPending.setText(String.valueOf(user.pendingCount));
        binding.label.setOnClickListener(new OnClick());
        binding.valueToConvert.addTextChangedListener(new HomeViewModel.convert(getContext()));

        if (user.pendingCount == 0) {
            binding.countPending.setVisibility(View.GONE);
        }

        Utils.setColorBarStatus(getContext());
        configTransactions();

        return binding.getRoot();
    }

    private void configTransactions() {
        transactions = new DatabaseHelper(getContext()).getAllTransaction();
        if (transactions.size() == 0) {
            binding.countHistory.setVisibility(View.VISIBLE);
        } else {
            binding.countHistory.setVisibility(View.GONE);
        }
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
            } else if (id == R.id.refresh) {
                viewModel.refresh();
            } else if (id == R.id.label) {
                viewModel.changeConvert(view);
            } else if (id == R.id.imageViewUser) {
                viewModel.moveToProfile();
            }
        }
    }
}
