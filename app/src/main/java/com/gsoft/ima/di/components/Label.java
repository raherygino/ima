package com.gsoft.ima.di.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsoft.ima.R;

public class Label extends RelativeLayout {

    public Label(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setPadding(35,14,14,20);
        this.setBackground(context.getDrawable(R.drawable.back_input));
        this.addView(Label(context, attrs));
    }

    private TextView Label(Context context, AttributeSet attrs) {

        TextView textView = new TextView(context);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "SF_PRO_REGULAR.otf");
        textView.setTypeface(typeface);

        textView.setTranslationY(22);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Label);
        String text = typedArray.getString(R.styleable.Label_text);

        if (text != null) {
            textView.setText(text);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(context.getColor(R.color.grey_400));
        }

        return textView;

    }

}
