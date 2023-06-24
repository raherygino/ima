package com.gsoft.ima.di.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.ima.R;
import com.gsoft.ima.model.models.SettingItem;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.utils.Utils;

import java.util.ArrayList;
import java.util.Set;

public class AdapterRecyclerSetting extends RecyclerView.Adapter<AdapterRecyclerSetting.MyHolder> {

    private final Context context;
    private final ArrayList<SettingItem> settingItems;
    private final RecyclerView recyclerView;

    public AdapterRecyclerSetting(Context context, ArrayList<SettingItem> settingItems, RecyclerView recyclerView) {
        this.context = context;
        this.settingItems = settingItems;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_setting, viewGroup, false);
        return new MyHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        SettingItem item = settingItems.get(i);
        myHolder.title_setting.setText(item.title);
        myHolder.detail_setting.setText(item.details);
        myHolder.image.setImageDrawable(Utils.getDrawable(context, item.icon));
        myHolder.image.setBackgroundDrawable(Utils.getDrawable(context, item.background));
    }

    private void setColorItem(MyHolder mHolder, int color)
    {
        mHolder.image.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void remove(int i)
    {
        settingItems.remove(i);
        recyclerView.removeAllViews();
    }

    @Override
    public int getItemCount() {
        return settingItems.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        TextView title_setting, detail_setting;
        ImageView image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title_setting = itemView.findViewById(R.id.title_setting);
            detail_setting = itemView.findViewById(R.id.detail_setting);
            image = itemView.findViewById(R.id.icon);
        }
    }
}

