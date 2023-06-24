package com.gsoft.ima.di.adapters.setting;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.ima.R;

public class ViewHolderSetting extends RecyclerView.ViewHolder
{
    public TextView username, type, amount, date;
    public ImageView image;

    public ViewHolderSetting(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.username);
        type = itemView.findViewById(R.id.type);
        amount = itemView.findViewById(R.id.amount);
        date = itemView.findViewById(R.id.date);
        image = itemView.findViewById(R.id.icon);
    }
}