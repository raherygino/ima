package com.gsoft.ima.ui.main.home.adapter;

import static com.gsoft.ima.constants.main.MainConstants.EMPTY;
import static com.gsoft.ima.constants.main.MainConstants.MESSAGE;
import static com.gsoft.ima.constants.main.MainConstants.STAT_PENDING;
import static com.gsoft.ima.constants.main.MainConstants.STAT_SENT;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.gsoft.ima.R;
import com.gsoft.ima.api.FetchData;
import com.gsoft.ima.api.RetrofitClient;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.di.dialog.ConfirmDialog;
import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.ui.main.home.HomeFragment;
import com.gsoft.ima.ui.main.send.SendFragment;
import com.gsoft.ima.utils.UserLogged;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        private final User user;

        public onItemClicked(Context context, ArrayList<Transaction> transactions, HomeAdapterHistory mAdapter) {
            this.transactions = transactions;
            this.context = context;
            this.mAdapter = mAdapter;
            this.user = UserLogged.data(context);
        }

        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

            switch (index) {
                case 0:
                    showDetails(context, position, transactions);
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

    public static void showDetails(Context context, int position, ArrayList<Transaction> transactions) {
        String message = "";
        User user = UserLogged.data(context);
        Transaction item = transactions.get(position);
        if (item.nameReceiver.equals(user.firstname)) {
            message = "Vous avez reçu un montant de "+item.amount+" IMA depuis "+item.nameSender;
            if (item.status.equals(STAT_PENDING) && item.method.equals(context.getString(R.string.network))) {
                ConfirmDialog dialog = new ConfirmDialog(context, item.method, message);
                dialog.btnOk.setText("Confirmer");
                dialog.btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        ProgressDialog dialogLoading = new ProgressDialog(context);
                        dialogLoading.setMessage("Loading");
                        dialogLoading.setCancelable(false);
                        dialogLoading.show();
                        RetrofitClient.confirmTransaction(item.id, item.amount, item.numReceiver)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        dialogLoading.dismiss();
                                        String message = "Empty";
                                        String result = "";
                                        if (response.isSuccessful()) {
                                            try {
                                                result = response.body().source().readUtf8();
                                                JSONObject jsonObject = new JSONObject(result);
                                                message = "Montant confirmé";
                                                if (!jsonObject.getString(MESSAGE).equals(STAT_SENT)) {
                                                    message = result;
                                                } else {
                                                    Thread thread = new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            MainActivity mainActivity = (MainActivity) context;
                                                            FetchData.getDataUserByPhone(context, item.numReceiver);
                                                            FetchData.getPendingSender(context, item.numReceiver);
                                                            mainActivity.runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    mainActivity.setFragment(new HomeFragment());
                                                                }
                                                            });
                                                        }
                                                    });
                                                    thread.start();
                                                }
                                            } catch (IOException | JSONException e) {
                                                e.printStackTrace();
                                                message = e.getMessage()+ result;

                                            }
                                        } else {
                                            message = "Error";
                                        }
                                        AlertDialog dialog1 = new AlertDialog(context, EMPTY, message);
                                        dialog1.show();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        dialogLoading.dismiss();
                                        AlertDialog dialog1 = new AlertDialog(context, EMPTY, t.getMessage());
                                        dialog1.show();
                                    }
                                });

                    }
                });
                dialog.show();
            } else {
                AlertDialog dialog = new AlertDialog(context, "Details", message);
                dialog.show();
            }
        } else {
            message = "Vous avez envoyés un montant de "+item.amount+" IMA vers "+item.nameReceiver;
            AlertDialog dialog = new AlertDialog(context, "Details", message);
            dialog.show();
        }
    }
}
