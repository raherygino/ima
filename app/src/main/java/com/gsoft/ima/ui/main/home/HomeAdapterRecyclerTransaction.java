package com.gsoft.ima.ui.main.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.ima.R;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.ui.main.setting.SettingViewHolder;

import java.util.ArrayList;

public class HomeAdapterRecyclerTransaction extends RecyclerView.Adapter<SettingViewHolder> {

    private final Context context;
    //private final ArrayList<Transaction> transaction;

    public HomeAdapterRecyclerTransaction(Context context, ArrayList<Transaction> nTransaction, RecyclerView recyclerView) {
        this.context = context;
     //   this.transaction = nTransaction;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, viewGroup, false);
        return new SettingViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder myHolder, int i) {
/*
        Transaction item = transaction.get(i);
        myHolder.username.setText(item.nameReceiver);
        myHolder.type.setText(item.type);
        myHolder.amount.setText(String.valueOf(item.amount));
        myHolder.date.setText(item.date);

        if (item.type.equals(KEY_SENT)) {
            setColorItem(myHolder, R.color.green_400);
            myHolder.image.setImageDrawable(Utils.getDrawable(context, IC_ARROW_OUT));
        } else {
            setColorItem(myHolder, R.color.red_400);
            myHolder.image.setImageDrawable(Utils.getDrawable(context, IC_ARROW_IN));
        }*/
    }

    private void setColorItem(SettingViewHolder mHolder, int color)
    {
        mHolder.image.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getItemCount() {
        //return transaction.size();
        return 0;
    }
}
