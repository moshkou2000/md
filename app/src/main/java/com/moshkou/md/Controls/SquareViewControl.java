package com.moshkou.md.Controls;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class SquareViewControl extends AppCompatImageView {

    public SquareViewControl(Context context) {
        super(context);
    }

    public SquareViewControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareViewControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());// + 108); //Snap to width
    }

}