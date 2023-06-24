package com.gsoft.ima.ui.main.setting;

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
import com.gsoft.ima.databinding.FragmentSettingBinding;
import com.gsoft.ima.di.adapters.AdapterRecyclerSetting;
import com.gsoft.ima.di.adapters.AdapterRecyclerTransaction;
import com.gsoft.ima.model.models.SettingItem;
import com.gsoft.ima.model.models.Transaction;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);

        ArrayList<SettingItem> settingItems = new ArrayList<>();
        settingItems.add(new SettingItem("Update", "Update your data from server", "ic_cloud", "back_icon_yellow"));
        settingItems.add(new SettingItem("Change language", "There is three differents language", "ic_flag", "back_icon_red"));
        settingItems.add(new SettingItem("Help", "You can contact Adminstrator", "ic_question", "back_icon_green"));
        settingItems.add(new SettingItem("About", "Learn more about IMA", "ic_info", "back_icon_blue"));
        binding.recyclerViewSetting.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTransaction = new LinearLayoutManager(getContext());
        binding.recyclerViewSetting.setLayoutManager(layoutManagerTransaction);
        AdapterRecyclerSetting adapterRecyclerSetting = new AdapterRecyclerSetting(getContext(), settingItems, binding.recyclerViewSetting);
        binding.recyclerViewSetting.setAdapter(adapterRecyclerSetting);

        return binding.getRoot();
    }
}
