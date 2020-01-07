package com.moshkou.md.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import com.moshkou.md.R;
import com.moshkou.md.configs.Settings;


public class DraggingPanel extends LinearLayout {


    private static String TAG = "DRAGGING";


    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private final int mQueenId = 123;//R.id.layout_draggable_content;

    private FrameLayout mQueenButton;
    private ViewDragHelper mDragHelper;

    private int mDraggingState = 0;
    private int mDraggingBorder;
    private int mVerticalRange;
    private boolean mIsOpen;
    private boolean mIsClose = true;


    public DraggingPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        mIsOpen = false;
    }

    @Override
    protected void onFinishInflate() {
        new DragHelperCallback();
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mIsOpen = false;
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mVerticalRange = h - (int) (96 * Settings.DEVICE_DENSITY); // 96dp: double size of height of bottom navigation-bar (48dp)
        close();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void onStopDraggingToClosed() { }

    private void onStartDragging() { }

    private boolean isQueenTarget(MotionEvent event) {
        int[] queenLocation = new int[2];
        mQueenButton.getLocationOnScreen(queenLocation);
        int upperLimit = queenLocation[1] + mQueenButton.getMeasuredHeight();
        int lowerLimit = queenLocation[1];
        int y = (int) event.getRawY();
        return (y > lowerLimit && y < upperLimit);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isQueenTarget(event) && mDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsOpen) {
            return true;
        } else if (isQueenTarget(event) || isMoving()) {
            mDragHelper.processTouchEvent(event);
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public void computeScroll() { // needed for automatic settling.
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public boolean isMoving() {
        return (mDraggingState == ViewDragHelper.STATE_DRAGGING ||
                mDraggingState == ViewDragHelper.STATE_SETTLING);
    }

    public boolean isOpen() {
        return mIsOpen;
    }

    public void initQueenButton() {
        mQueenButton = findViewById(mQueenId);
    }

    public void close() {
        mIsOpen = true;
        mDragHelper.captureChildView(mQueenButton, mQueenId);
        mDragHelper.dragTo(0, 200, 0, 300);
//        ViewCompat.offsetTopAndBottom(mQueenButton, 300);

//        mQueenButton.setY(mVerticalRange);
    }

    public void hide() {
        mQueenButton.setY(mVerticalRange + (int) (48 * Settings.DEVICE_DENSITY));
    }


    public class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public void onViewDragStateChanged(int state) {
            if (state == mDraggingState) { // no change
                return;
            }
            if ((mDraggingState == ViewDragHelper.STATE_DRAGGING || mDraggingState == ViewDragHelper.STATE_SETTLING) &&
                    state == ViewDragHelper.STATE_IDLE) {
                // the view stopped from moving.

                if (mDraggingBorder == 0) {
                    onStopDraggingToClosed();
                } else if (mDraggingBorder == mVerticalRange) {
                    mIsOpen = true;
                }
            }
            if (state == ViewDragHelper.STATE_DRAGGING) {
                onStartDragging();
            }
            mDraggingState = state;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mDraggingBorder = top;
            Log.i(TAG, "top: " + top);
        }

        public int getViewVerticalDragRange(View child) {
            return mVerticalRange;
        }

        @Override
        public boolean tryCaptureView(View view, int i) {
            return (view.getId() == mQueenId);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = mVerticalRange;
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final float rangeToCheck = mVerticalRange;
            if (mDraggingBorder == 0) {
                mIsOpen = false;
                return;
            }
            if (mDraggingBorder == rangeToCheck) {
                mIsOpen = true;
                return;
            }
            boolean settleToOpen = false;
            if (yvel > AUTO_OPEN_SPEED_LIMIT) { // speed has priority over position
                settleToOpen = true;
            } else if (yvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false;
            } else if (mDraggingBorder > rangeToCheck / 2) {
                settleToOpen = true;
            } else if (mDraggingBorder < rangeToCheck / 2) {
                settleToOpen = false;
            }

            final int settleDestY = settleToOpen ? mVerticalRange : 0;

            if(mDragHelper.settleCapturedViewAt(0, settleDestY)) {
                ViewCompat.postInvalidateOnAnimation(DraggingPanel.this);
            }
        }


    }
}