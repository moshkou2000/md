package com.moshkou.md.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshkou.md.R;

public class LevelsActivity extends FragmentActivity {


    private Context context = this;

    private TextView previous;
    private TextView current;
    private TextView next;
    private TextView all;
    private TextView message;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        previous = findViewById(R.id.previous);
        current = findViewById(R.id.current);
        next = findViewById(R.id.next);
        all = findViewById(R.id.all);
        message = findViewById(R.id.message);
        image = findViewById(R.id.image);

        step1();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void step1() {

//        previous.animate().x(previous.getX()).y(previous.getY()).setDuration(10)
//                .x(current.getX()).y(current.getY()).scaleX(2.2f).scaleY(2.2f).setDuration(2000)
//                .start();

        AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(3000);
        animation1.setStartOffset(5000);
        animation1.setFillAfter(true);
        previous.startAnimation(animation1);

        //previous.animate().translationY(0).alpha(1.0f).setListener(null);

//        Path path = new Path();
//        path.arcTo(0f, 0f, 0f, 0f, 270f, 160f, true);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(previous, View.X, View.Y, path);
//        animator.setDuration(1000);
//        animator.start();
    }

}
