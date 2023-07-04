package com.gsoft.ima.di.dialog;


import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.gsoft.ima.R;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.utils.Utils;

public class SendDialog extends SweetDialog {
    public Button btnOk;

    public SendDialog(Context context)
    {
        super(context, R.layout.dialog_send, null, null);
        setWidthLayout();
        setCancelable(false);
        setAnimation(R.style.DialogAnimationTranslate);
        EditText sendBy = (EditText) findViewById(R.id.send_by);
        Utils.setOnHoverLabel(sendBy);
        sendBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.type_send, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        sendBy.setText(menuItem.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        onCancel(R.id.btn_cancel);
    }
    @Override
    public void show() {
        super.show();
    }
}

