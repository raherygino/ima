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
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.utils.Utils;

import java.util.ArrayList;

public class AdapterRecyclerTransaction extends RecyclerView.Adapter<AdapterRecyclerTransaction.MyHolder> {

    private final Context context;
  //  private final Utils utils;
    private final ArrayList<Transaction> transaction;
    private final RecyclerView recyclerView;

    public AdapterRecyclerTransaction(Context context, ArrayList<Transaction> nTransaction, RecyclerView recyclerView) {
        this.context = context;
        this.transaction = nTransaction;
   //     this.utils = new Utils(context);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, viewGroup, false);
        return new MyHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        Transaction item = transaction.get(i);
        myHolder.username.setText(item.nameReceiver);
        myHolder.type.setText(item.type);
        myHolder.amount.setText("90 000");
        myHolder.date.setText(item.date);
        if (item.type.equals("sent")) {
            setColorItem(myHolder, R.color.green_400);
            myHolder.image.setImageDrawable(Utils.getDrawable(context, "ic_arrow_out"));
        } else {
            setColorItem(myHolder, R.color.red_400);
            myHolder.image.setImageDrawable(Utils.getDrawable(context, "ic_arrow_in"));
        }
        //myHolder.itemView.setOnClickListener(new onClick(i));
    }

    private void setColorItem(MyHolder mHolder, int color)
    {
        mHolder.image.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void remove(int i)
    {
        transaction.remove(i);
        recyclerView.removeAllViews();
    }

    @Override
    public int getItemCount() {
        return transaction.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        TextView username, type, amount, date;
        ImageView image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            type = itemView.findViewById(R.id.type);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.icon);
        }
    }
}
