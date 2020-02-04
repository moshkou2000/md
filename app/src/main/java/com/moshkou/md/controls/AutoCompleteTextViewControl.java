package com.moshkou.md.controls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;


@SuppressLint("AppCompatCustomView")
public class AutoCompleteTextViewControl extends AutoCompleteTextView {

    public AutoCompleteTextViewControl(Context context) {
        super(context);
    }

    public AutoCompleteTextViewControl(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public AutoCompleteTextViewControl(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if (focused && getFilter() != null) {
//            performFiltering(getText(), android.view.KeyEvent.KEYCODE_ENTER);
            showDropDown();
        }
    }

}