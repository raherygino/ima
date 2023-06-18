package com.gsoft.ima.di.dialog;

import android.content.Context;
import android.widget.Button;

import com.gsoft.ima.R;

public class AlertDialog extends SweetDialog {
    public Button btnOk;

    public AlertDialog(Context context, String title, String message)
    {
        super(context, R.layout.dialog_confirm, title, message);
        btnOk = BTN_OK;
        btnOk.setText(context.getString(R.string.yes));
        BTN_CANCEL.setText(context.getString(R.string.no));
        onCancel(R.id.btn_cancel);
    }
    @Override
    public void show() {
        super.show();
    }
}