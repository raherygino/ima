package com.gsoft.ima.di.components;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;

import com.gsoft.ima.R;

public class EditText extends AppCompatEditText {
    private final Context mContext;
    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        Label layoutLabel = (Label) super.getParent();
        TextView label = (TextView) layoutLabel.getChildAt(0);
        if (focused) {
            layoutLabel.setBackground(mContext.getDrawable(R.drawable.back_input_hover));
            label.setTextColor(getColor(R.color.blue_500));
            label.animate().translationY(-8).setDuration(100).start();
        } else {
            layoutLabel.setBackground(mContext.getDrawable(R.drawable.back_input));
            label.setTextColor(getColor(R.color.grey_400));
            if (this.getText().toString().isEmpty()) {
                label.animate().translationY(22).start();
            }
        }
    }

    private int getColor(int idColor) {
        int color = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = mContext.getColor(idColor);
        }
        return color;
    }
}
