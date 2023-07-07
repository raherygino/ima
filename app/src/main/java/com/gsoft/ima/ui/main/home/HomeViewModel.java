package com.gsoft.ima.ui.main.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.api.FetchData;
import com.gsoft.ima.databinding.FragmentHomeBinding;
import com.gsoft.ima.di.dialog.AlertDialog;
import com.gsoft.ima.model.database.DatabaseHelper;
import com.gsoft.ima.model.models.User;
import com.gsoft.ima.ui.auth.AuthActivity;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.utils.Utils;

import dmax.dialog.SpotsDialog;

public class HomeViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    public User user;
    private FragmentHomeBinding binding;

    public HomeViewModel(Context context, FragmentHomeBinding binding) {
        this.context = context;
        DatabaseHelper db = new DatabaseHelper(context);
        this.user = db.User();
        this.binding = binding;
    }

    public void logout() {
        DatabaseHelper db = new DatabaseHelper(context);
        if (db.deleteUser() != -1) {
            Intent intent = new Intent(context, AuthActivity.class);
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.finish();
        } else {
            AlertDialog dialog = new AlertDialog(context,context.getString(R.string.error),context.getString(R.string.error_logout));
            dialog.show();
        }
    }

    public void refresh() {

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("Loading");
        dialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FetchData.getDataUserByPhone(context, user.phone);
                FetchData.getPendingSender(context, user.phone);
                MainActivity mainActivity = (MainActivity) context;
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setFragment(new HomeFragment());
                        dialog.dismiss();
                    }
                });
            }
        });
        thread.start();
    }

    public void changeConvert(View view) {
        PopupMenu menu = new PopupMenu(context, view);
        menu.getMenuInflater().inflate(R.menu.unity, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.convert = menuItem.getTitle().toString();
                String imaLabel = context.getString(R.string.app_name);
                ((TextView) view).setText(mainActivity.convert);
                if (mainActivity.convert.equals(imaLabel)) {
                    binding.labelTo.setText(context.getString(R.string.mga));
                    binding.imageViewIma.setImageDrawable(context.getDrawable(R.drawable.mg_flag));
                    binding.imageViewMg.setImageDrawable(context.getDrawable(R.drawable.ic_ima_black));
                } else {
                    binding.labelTo.setText(imaLabel);
                    binding.imageViewMg.setImageDrawable(context.getDrawable(R.drawable.mg_flag));
                    binding.imageViewIma.setImageDrawable(context.getDrawable(R.drawable.ic_ima_black));
                }
                return true;
            }
        });
        menu.show();
    }

    public static class convert implements TextWatcher {
        private Context context;

        public convert(Context context) {
            this.context = context;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            MainActivity mainActivity = (MainActivity) context;
            TextView textView = ((Activity) context).findViewById(R.id.amount_ima);
            if (charSequence.length() > 0 ) {
                double result = 0;
                if (mainActivity.convert.equals(context.getString(R.string.mga))) {
                    result = Double.parseDouble(charSequence.toString()) / 200;
                } else {
                    result = Double.parseDouble(charSequence.toString()) * 200;
                }

                textView.setText(Utils.formatNumber(result));
            } else {
                textView.setText("0");
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
