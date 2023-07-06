package com.gsoft.ima.ui.main.home.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.gsoft.ima.R;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.utils.UserLogged;

import java.util.ArrayList;

public class HomeAdapterHistory extends BaseAdapter {

    private final ArrayList<Transaction> transactions;
    private final Context context;
    private final User user;

    public HomeAdapterHistory(Context context, ArrayList<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
        this.user = UserLogged.data(context);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.item_transaction, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Transaction item = getItem(position);

        if (item.nameSender.equals(user.firstname)) {
            holder.username.setText(item.nameReceiver);
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_out));
            holder.icon.setColorFilter(context.getColor(R.color.red_400));
        } else {
            holder.username.setText(item.nameSender);
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_in));
            holder.icon.setColorFilter(context.getColor(R.color.green_400));
        }

        holder.type.setText(item.status);

        if (item.status.equals("sent") && item.nameReceiver.equals(user.firstname)) {
            holder.type.setText("received");
        }

        holder.date.setText(item.date);
        holder.amount.setText(String.valueOf(item.amount));
        holder.icon.setOnClickListener(new onClick(position));
        holder.username.setOnClickListener(new onClick(position));

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

        private final int position ;

        public onClick(int pos) {
            this.position = pos;
        }

        @Override
        public void onClick(View view) {
            HomeAdapterMenu.showDetails(context, position, transactions);
        }
    }
}
