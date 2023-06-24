package com.gsoft.ima.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.gsoft.ima.R;
import com.gsoft.ima.di.components.EditText;
import com.gsoft.ima.di.components.Label;

public class Utils {

    public static void setColorBarStatus(Context context) {
        Activity activity = (Activity) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(activity.getColor(R.color.grey_50));
                View decor = activity.getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getDrawable(Context context, String uri) {
        @SuppressLint("DiscouragedApi") int imageResource = context.getResources().getIdentifier("@drawable/"+uri, null, context.getPackageName());
        return context.getResources().getDrawable(imageResource);
    }

    public static void setColor(Context context, View v, int color) {
        if (v instanceof ImageView) {
            ImageView image = (ImageView) v;
            image.setColorFilter(
                    ContextCompat.getColor(context, color),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else if(v instanceof TextView){
            TextView txt = (TextView) v;
            txt.setTextColor(ContextCompat.getColor(context, color));
        }
    }


    public static void setOnHoverLabel(EditText editText) {
        Label layoutLabel = (Label) editText.getParent();
        TextView label = (TextView) layoutLabel.getChildAt(0);
        label.animate().translationY(-8).setDuration(100).start();
    }

    public static void effectClick(Context context, View view) {
        final Animation anim = AnimationUtils.loadAnimation(context, R.anim.btn_click);
        view.startAnimation(anim);
    }
}
