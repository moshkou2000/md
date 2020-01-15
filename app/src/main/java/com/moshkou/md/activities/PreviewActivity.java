package com.moshkou.md.activities;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.VideoView;

import com.moshkou.md.configs.Keys;
import com.moshkou.md.R;
import com.squareup.picasso.Picasso;

public class PreviewActivity extends AppCompatActivity {


    private boolean hasFocus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        hideSystemUI();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get extra
        Intent i = getIntent();
        String url = i.getStringExtra(Keys.URL);
        String name = i.getStringExtra(Keys.NAME);
        boolean isVideo = i.getBooleanExtra(Keys.VIDEO, false);

        setTitle(name);

        if (!isVideo) {
            ImageView image = findViewById(R.id.image);
            image.setOnClickListener(view -> {
                if (!hasFocus) {
                    hideSystemUI();
                } else {
                    showSystemUI();
                }
                hasFocus = !hasFocus;
            });
            if (image.getVisibility() != View.VISIBLE)
                image.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(image);
        } else {
            VideoView video = findViewById(R.id.video);
            video.setOnClickListener(view -> {
                if (!hasFocus) {
                    hideSystemUI();
                } else {
                    showSystemUI();
                }
                hasFocus = !hasFocus;
            });
            if (video.getVisibility() != View.VISIBLE)
                video.setVisibility(View.VISIBLE);
            video.setVideoURI(Uri.parse(url));
            video.start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    private void showSystemUI() {
        // Shows the system bars by removing all the flags
        // except for the ones that make the content appear under the system bars.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
