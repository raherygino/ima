package com.gsoft.ima.di.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.gsoft.ima.R;

public class CustomEditText extends AppCompatEditText {
    @SuppressLint("UseCompatLoadingForDrawables")
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        this.setBackground(getResources().getDrawable(R.drawable.back_input));
        this.setPadding(22,22,20,22);
    }
    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);

        String customFont = typedArray.getString(R.styleable.CustomView_customFont);
        if (customFont != null) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), customFont);
            setTypeface(typeface);
        }

        typedArray.recycle();
    }
}
