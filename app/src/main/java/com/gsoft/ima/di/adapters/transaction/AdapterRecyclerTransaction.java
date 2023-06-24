package com.gsoft.ima.di.adapters.transaction;

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
import com.gsoft.ima.di.adapters.setting.ViewHolderSetting;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.utils.Utils;

import java.util.ArrayList;

public class AdapterRecyclerTransaction extends RecyclerView.Adapter<ViewHolderSetting> {

    private final Context context;
    private final ArrayList<Transaction> transaction;

    public AdapterRecyclerTransaction(Context context, ArrayList<Transaction> nTransaction, RecyclerView recyclerView) {
        this.context = context;
        this.transaction = nTransaction;
    }

    @NonNull
    @Override
    public ViewHolderSetting onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, viewGroup, false);
        return new ViewHolderSetting(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderSetting myHolder, int i) {

        Transaction item = transaction.get(i);
        myHolder.username.setText(item.nameReceiver);
        myHolder.type.setText(item.type);
        myHolder.amount.setText(String.valueOf(item.amount));
        myHolder.date.setText(item.date);

        if (item.type.equals("sent")) {
            setColorItem(myHolder, R.color.green_400);
            myHolder.image.setImageDrawable(Utils.getDrawable(context, "ic_arrow_out"));
        } else {
            setColorItem(myHolder, R.color.red_400);
            myHolder.image.setImageDrawable(Utils.getDrawable(context, "ic_arrow_in"));
        }
    }

    private void setColorItem(ViewHolderSetting mHolder, int color)
    {
        mHolder.image.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getItemCount() {
        return transaction.size();
    }
}
