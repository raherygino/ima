package com.gsoft.ima.di.dialog;

import android.content.Context;
import android.widget.Button;

import com.gsoft.ima.R;

public class AlertDialog extends SweetDialog {
    public Button btnOk;

    public AlertDialog(Context context, String title, String message)
    {
        super(context, R.layout.dialog_alert, title, message);
        onCancel(R.id.btn_cancel);
    }
    @Override
    public void show() {
        super.show();
    }
}