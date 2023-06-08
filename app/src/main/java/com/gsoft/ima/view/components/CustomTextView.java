package com.gsoft.ima.view.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

import com.gsoft.ima.R;

public class CustomTextView extends AppCompatTextView {
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(attrs);
    }

    private void setFont(AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);

        String customFont = typedArray.getString(R.styleable.CustomEditText_customFont);
        if (customFont != null) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), customFont);
            setTypeface(typeface);
        }

        typedArray.recycle();

    }
}
