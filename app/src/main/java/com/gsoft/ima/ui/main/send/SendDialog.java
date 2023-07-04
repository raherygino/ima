package com.gsoft.ima.ui.main.send;


import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.gsoft.ima.R;
import com.gsoft.ima.databinding.DialogSendBinding;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.di.dialog.SweetDialog;
import com.gsoft.ima.utils.Utils;

public class SendDialog extends SweetDialog {

    private final DialogSendBinding binding;
    private final SendViewModel viewModel;

    public SendDialog(Context context)
    {
        super(context, R.layout.dialog_send, null, null);
        this.binding = DialogSendBinding.inflate(((Activity)context).getLayoutInflater());
        this.setLayout(binding.getRoot());
        this.config();
        this.onCancel(R.id.btn_cancel);
        this.viewListener();
        this.viewModel = new SendViewModel(context, binding);
    }

    private void config() {
        setWidthLayout();
        setCancelable(false);
        setAnimation(R.style.DialogAnimationTranslate);
    }

    private void viewListener() {
        Utils.setOnHoverLabel(binding.sendBy);
        binding.sendBy.setOnClickListener(new onClick());
        binding.btnOk.setOnClickListener(new onClick());
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.send_by) {
                viewModel.onChangeType();
            } else if (id == R.id.btn_ok) {
                viewModel.send();
            }
        }
    }

    @Override
    public void show() {
        super.show();
    }
}

