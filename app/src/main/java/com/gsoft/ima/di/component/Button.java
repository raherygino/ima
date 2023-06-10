package com.gsoft.ima.di.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.gsoft.ima.R;

public class Button extends AppCompatButton {
    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(attrs);
        setConfig(attrs);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setConfig(AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
        String variant = typedArray.getString(R.styleable.CustomView_variant);
        this.setAllCaps(false);

        if (variant == null || variant.equals("primary")) {
            this.setBackground(getResources().getDrawable(R.drawable.back_btn_primary));
            this.setTextColor(getResources().getColor(R.color.white));
        } else if (variant.equals("success")) {
            this.setBackground(getResources().getDrawable(R.drawable.back_btn_success));
            this.setTextColor(getResources().getColor(R.color.white));
        } else if (variant.equals("soft_primary")) {
            this.setBackground(getResources().getDrawable(R.drawable.back_btn_soft_primary));
            this.setTextColor(getResources().getColor(R.color.blue_500));
        }

        typedArray.recycle();
    }

    private void setFont(AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);

        String customFont = typedArray.getString(R.styleable.CustomView_customFont);
        if (customFont != null) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), customFont);
            setTypeface(typeface);
        }

        typedArray.recycle();

    }
}
