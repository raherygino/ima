package com.gsoft.ima.ui.main.home.adapter;

import static com.gsoft.ima.constants.main.MainConstants.EMPTY;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.gsoft.ima.R;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.di.dialog.ConfirmDialog;
import com.gsoft.ima.model.models.Transaction;

import java.util.ArrayList;

public class HomeAdapterMenu implements SwipeMenuCreator {

    private final Context context;

    public HomeAdapterMenu(Context context) {
        this.context = context;
    }

    @Override
    public void create(SwipeMenu menu) {

        SwipeMenuItem openItem = new SwipeMenuItem(context);
        openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,0xCE)));
        openItem.setWidth(dp2px(70));
        openItem.setTitle("Open");
        openItem.setTitleSize(18);
        openItem.setTitleColor(Color.WHITE);
        menu.addMenuItem(openItem);

        SwipeMenuItem deleteItem = new SwipeMenuItem(context);
        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
        deleteItem.setWidth(dp2px(60));
        deleteItem.setIcon(R.drawable.ic_delete_24);
        deleteItem.setTitleColor(Color.DKGRAY);
        menu.addMenuItem(deleteItem);
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,context.getResources().getDisplayMetrics());
    }

    public static class onItemClicked implements SwipeMenuListView.OnMenuItemClickListener{

        private final ArrayList<Transaction> transactions;
        private final Context context;
        private final HomeAdapterHistory mAdapter;

        public onItemClicked(Context context, ArrayList<Transaction> transactions, HomeAdapterHistory mAdapter) {
            this.transactions = transactions;
            this.context = context;
            this.mAdapter = mAdapter;
        }

        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
            Transaction item = transactions.get(position);

            switch (index) {
                case 0:
                    AlertDialog dialog = new AlertDialog(context, "Details", item.nameReceiver);
                    dialog.show();
                    break;

                case 1:
                    ConfirmDialog confirmDialog = new ConfirmDialog(context, EMPTY, "You want to delete it ?");
                    confirmDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            transactions.remove(position);
                            mAdapter.notifyDataSetChanged();
                            confirmDialog.cancel();
                        }
                    });
                    confirmDialog.show();
                    break;
            }
            return false;
        }
    }
}
