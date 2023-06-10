package com.gsoft.ima.di.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.graphics.drawable.ColorDrawable;
import android.app.Activity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ActionMenuView.LayoutParams;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import com.gsoft.ima.R;


public class SweetDialog{
    public Dialog dialog;
    private final Activity activity;
    private final int idLay;

    public Button BTN_OK;

    public SweetDialog(Context ctx, int idLay, String valueTitle, String valueDesc){
        this.activity = (Activity) ctx;
        this.dialog = new Dialog(ctx);
        this.idLay = idLay;
        this.initDialog();
        this.BTN_OK = dialog.findViewById(R.id.btn_ok);
        if (valueTitle != null) {
            TextView title = dialog.findViewById(R.id.title_dialog);
            title.setText(valueTitle);
            TextView desc = dialog.findViewById(R.id.description_dialog);
            desc.setText(valueDesc);
        }

    }

    private void initDialog(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.width = display.getWidth();
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(idLay);
        dialog.setCancelable(true);

        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(lp.width-200, LayoutParams.WRAP_CONTENT);

        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setAttributes(lp);
    }

    public View findViewById(int id) {
        return dialog.findViewById(id);
    }

    public void setLayout(int lay) {
        dialog.setContentView(lay);
    }


    public void show(){
        dialog.show();
    }

    public void onCancel(int id) {
        Button btn = dialog.findViewById(id);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.cancel();
            }
        });
    }

    public void cancel()
    {
        dialog.cancel();
    }
}



