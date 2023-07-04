package com.gsoft.ima.ui.main.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gsoft.ima.R;
import com.gsoft.ima.model.models.Transaction;

import java.util.ArrayList;

public class HomeAdapterHistory extends BaseAdapter {

    private final ArrayList<Transaction> transactions;
    private final Context context;

    public HomeAdapterHistory(Context context, ArrayList<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.item_transaction, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Transaction item = getItem(position);

        holder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_in));
        holder.username.setText(item.nameSender);
        holder.date.setText(item.date);
        holder.amount.setText(String.valueOf(item.amount));
        holder.type.setText(item.method);
        holder.icon.setOnClickListener(new onClick());
        holder.username.setOnClickListener(new onClick());

        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView username, type, date, amount;

        public ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            username = (TextView) view.findViewById(R.id.username);
            type = (TextView) view.findViewById(R.id.type);
            amount = (TextView) view.findViewById(R.id.amount);
            date = (TextView) view.findViewById(R.id.date);
            view.setTag(this);
        }
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
