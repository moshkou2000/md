package com.moshkou.md.animations;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

public class TextAnimation {
    private Handler handler;
    private long startTime, currentTime, finishedTime = 0L;
    private int duration = 22000 / 4;// 1 character is equal to 1 second. if want to
    private int endTime = 0;


    public void textChangeColor(final TextView textView) {
        handler = new Handler();
        startTime = Long.valueOf(System.currentTimeMillis());
        currentTime = startTime;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentTime = Long.valueOf(System.currentTimeMillis());
                finishedTime = Long.valueOf(currentTime) - Long.valueOf(startTime);

                if (finishedTime >= duration + 30) {
                } else {
                    endTime = (int) (finishedTime / 250);// divide this by 1000,500,250,125
                    Spannable spannableString = new SpannableString(textView.getText());
                    spannableString.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, endTime, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    textView.setText(spannableString);
                    handler.postDelayed(this, 10);
                }
            }
        }, 10);
    }


    public void marque(final TextView textViewFirst, final TextView textViewSecond) {
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(9000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = textViewFirst.getWidth();
                final float translationX = width * progress;
                textViewFirst.setTranslationX(translationX);
                textViewSecond.setTranslationX(translationX - width);
            }
        });
        animator.start();
    }

}
