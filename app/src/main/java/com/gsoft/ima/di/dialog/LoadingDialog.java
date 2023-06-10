package com.gsoft.ima.di.dialog;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.gsoft.ima.R;

public class LoadingDialog extends SweetDialog{
    public Button btnOk;

    public LoadingDialog(Context context)
    {
        super(context, R.layout.dialog_loading, null, null);
        ImageView spinner = (ImageView) findViewById(R.id.spinner);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_rotate);
        spinner.startAnimation(animation);
    }
    @Override
    public void show() {
        super.show();
    }
}
