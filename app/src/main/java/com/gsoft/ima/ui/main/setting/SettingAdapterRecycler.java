package com.gsoft.ima.ui.main.setting;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.ima.R;
import com.gsoft.ima.api.FetchData;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.di.dialog.PromptDialog;
import com.gsoft.ima.model.models.SettingItem;
import com.gsoft.ima.utils.UserLogged;
import com.gsoft.ima.utils.Utils;

import java.util.ArrayList;

public class SettingAdapterRecycler extends RecyclerView.Adapter<SettingAdapterRecycler.MyHolder> {

    private final Context context;
    private final ArrayList<SettingItem> settingItems;
    private final RecyclerView recyclerView;

    public SettingAdapterRecycler(Context context, ArrayList<SettingItem> settingItems, RecyclerView recyclerView) {
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
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.effectClick(context, view);
                switch (i) {
                    case 0:
                        FetchData.getDataUserByPhone(context, UserLogged.data(context).phone);
                        FetchData.getPendingSender(context, UserLogged.data(context).phone);
                        Toast.makeText(context, "Request sent!", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        PopupMenu popupMenu = new PopupMenu(context, myHolder.detail_setting);
                        popupMenu.getMenuInflater().inflate(R.menu.language, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                myHolder.detail_setting.setText(menuItem.getTitle());
                                return true;
                            }
                        });
                        popupMenu.show();

                        break;
                    case 2:
                        AlertDialog helpDialog = new AlertDialog(context,context.getString(R.string.help) ,context.getString(R.string.help_details));
                        helpDialog.show();
                        break;
                    case 3:
                        AlertDialog dialog = new AlertDialog(context, context.getString(R.string.about),context.getString(R.string.about_details));
                        dialog.show();
                        break;
                }
            }
        });
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

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView title_setting, detail_setting;
        ImageView image, chevron_right;
        public View itemView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            title_setting = itemView.findViewById(R.id.title_setting);
            detail_setting = itemView.findViewById(R.id.detail_setting);
            image = itemView.findViewById(R.id.icon);
            chevron_right = itemView.findViewById(R.id.chevron_right);
        }
    }
}

