package com.gsoft.ima.di.dialog;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gsoft.ima.R;
import com.gsoft.ima.di.components.Label;

public class PromptDialog extends SweetDialog {
    public Button btnOk;
    public EditText editText;

    public PromptDialog(Context context, String title, String message, String label)
    {
        super(context, R.layout.dialog_prompt, title, message);
        btnOk = super.BTN_OK;
        editText = (EditText) super.findViewById(R.id.edit_query);
        Label layoutLabel = (Label) editText.getParent();
        TextView textViewlabel = (TextView) layoutLabel.getChildAt(0);
        textViewlabel.setText(label);
        onCancel(R.id.btn_cancel);
    }
    @Override
    public void show() {
        super.show();
    }
}
