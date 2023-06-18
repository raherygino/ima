package com.gsoft.ima.di.dialog;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.gsoft.ima.R;

public class PromptDialog extends SweetDialog {
    public Button btnOk;
    public EditText editText;

    public PromptDialog(Context context, String title, String message)
    {
        super(context, R.layout.dialog_prompt, title, message);
        btnOk = super.BTN_OK;
        editText = (EditText) super.findViewById(R.id.edit_query);
        onCancel(R.id.btn_cancel);
    }
    @Override
    public void show() {
        super.show();
    }
}
