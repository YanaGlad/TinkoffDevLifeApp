package com.example.tinkoffsiriusapp.graphics;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MyCompatTextView extends AppCompatTextView {

    public MyCompatTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyCompatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCompatTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "second.ttf");
        setTypeface(tf);
        setTextColor(Color.WHITE);
    }

}
