package com.moshkou.md.activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moshkou.md.configs.Data;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.helpers.SharedPreferencesSupport;
import com.moshkou.md.models.KeyValue;
import com.moshkou.md.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LevelsActivity extends FragmentActivity {


    private final Context context = this;

    private TextView level0;
    private TextView level1;
    private TextView level2;
    private TextView level3;
    private TextView all;
    private TextView message;
    private ImageView image;

    private int duration = 2000;
    private int width = 0;
    private float x = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        level0 = findViewById(R.id.level0);
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        all = findViewById(R.id.all);
        message = findViewById(R.id.message);
        image = findViewById(R.id.image);


        String strLevels = SharedPreferencesSupport.getString(context, Keys.LEVELS);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<KeyValue>>() { }.getType();
        List<KeyValue> levels = gson.fromJson(strLevels, type);

        if (levels != null && levels.size() > 0) {

            Boolean flag = true;
            int index;
            int size = levels.size();

            for (index = 0; flag && index < size; index++) {
                if ((Boolean) levels.get(index).value) {
                    flag = false;
                }
            }

            all.setText("OF " + size);
            message.setText("Do great");

            level0.setText(levels.get(index + 1) != null ? levels.get(index + 1).key : "");
            level1.setText(levels.get(index) != null ? levels.get(index).key : "");
            level2.setText(levels.get(index - 1) != null ? levels.get(index - 1).key : "");
            level3.setText(levels.get(index - 2) != null ? levels.get(index - 2).key : "");


            if (index == size) {
                // last level
            } else if (index > size - 1) {
                // last second
            } else if (index > size - 2) {
                // last third
            }

        }

        level0.measure(0, 0);
        width = level0.getMeasuredWidth();
        x = (float) ( -1 * (Data.DEVICE_WIDTH / 2 - width / 2));

        motion();
        colors();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void colors() {

        int cp = context.getColor(R.color.colorPrimary);

        final Window window = getWindow();


        ObjectAnimator.ofObject(window.getDecorView(), "backgroundColor", new ArgbEvaluator(), Color.WHITE, cp)
                .setDuration(duration)
                .start();
    }

    private void motion() {

        level0.animate()
                .translationX(x - width / 6)
                .translationY(0)
                .setDuration(duration)
                .start();

        level1.animate()
                .scaleX(2f)
                .scaleY(2f)
                .translationX(x)
                .translationY(-164)
                .setDuration(duration)
                .start();

        level2.animate()
                .translationX(x)
                .translationY(0)
                .setDuration(duration)
                .start();

        level3.animate()
                .translationX(x)
                .translationY(0)
                .setDuration(duration)
                .start();
    }

}
