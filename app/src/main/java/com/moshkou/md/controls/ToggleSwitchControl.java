package com.moshkou.md.controls;

import android.content.Context;
import android.util.AttributeSet;


public class ToggleSwitchControl extends BaseToggleSwitch {


    private int mCheckedTogglePosition;

    public ToggleSwitchControl(Context context) {
        this(context, null);
    }

    public ToggleSwitchControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getCheckedTogglePosition() {
        return mCheckedTogglePosition;
    }

    @Override
    protected void onClickOnToggleSwitch(int position) {
        setCheckedTogglePosition(position);
    }

    public void setCheckedTogglePosition(int position) {
        setCheckedTogglePosition(position, true);
    }

    public void setCheckedTogglePosition(int position, boolean notifyListener) {
        disableAll();
        activate(position);
        setSeparatorVisibility(position);
        mCheckedTogglePosition = position;
        if (notifyListener)
            notifyOnToggleChange(position);
    }

    private void setSeparatorVisibility(int activeIndex) {
        for (int i = 0; i < getToggleSwitchesContainer().getChildCount() - 1; i++) {
            ToggleSwitchButton toggleSwitchButton = new ToggleSwitchButton(getToggleSwitchesContainer().getChildAt(i));
            if (i == activeIndex || i == (activeIndex - 1))
                toggleSwitchButton.hideSeparator();
            else
                toggleSwitchButton.showSeparator();
        }
    }

    @Override
    protected void buildToggleButtons() {
        super.buildToggleButtons();
        setCheckedTogglePosition(0);
    }

    @Override
    protected boolean isActive(int position) {
        return mCheckedTogglePosition == position;
    }

}