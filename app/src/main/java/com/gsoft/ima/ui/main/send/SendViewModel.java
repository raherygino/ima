package com.gsoft.ima.ui.main.send;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.lifecycle.ViewModel;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.DialogSendBinding;

public class SendViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final DialogSendBinding binding;

    public SendViewModel(Context context, DialogSendBinding binding) {
        this.binding = binding;
        this.context = context;
    }

    public void onChangeType() {
        PopupMenu popupMenu = new PopupMenu(context, binding.sendBy);
        popupMenu.getMenuInflater().inflate(R.menu.type_send, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                binding.sendBy.setText(menuItem.getTitle());
                return true;
            }
        });
        popupMenu.show();
    }
}
