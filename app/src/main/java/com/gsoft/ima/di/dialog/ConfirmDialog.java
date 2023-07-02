package com.gsoft.ima.di.dialog;

import android.content.Context;
import android.widget.Button;

import com.gsoft.ima.R;

public class ConfirmDialog extends SweetDialog {
    public Button btnOk;

    public ConfirmDialog(Context context, String title, String message)
    {
        super(context, R.layout.dialog_confirm, title, message);
        this.btnOk = BTN_OK;
        onCancel(R.id.btn_cancel);
    }
    @Override
    public void show() {
        super.show();
    }
}