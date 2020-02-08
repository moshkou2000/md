package com.moshkou.md.activities;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.VideoView;

import com.moshkou.md.configs.Keys;
import com.moshkou.md.R;
import com.moshkou.md.fragments.PreviewItemFragment;
import com.moshkou.md.interfaces.OnImageListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class PreviewActivity extends AppCompatActivity implements
        OnImageListener {


    private List<Fragment> previewPages = new ArrayList<>();
    private boolean hasFocus = true;


    /**
     * Override functions ->
     * onCreate
     * onOptionsItemSelected
     * hideSystemUI
     * showSystemUI
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        hideSystemUI();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getExtra();
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


    /**
     * Helper function ->
     * getExtra
     * populatePreviewImages
     * populatePreviewVideos
     */

    private void getExtra() {
        Intent i = getIntent();
        String name = i.getStringExtra(Keys.NAME);
        boolean isVideo = i.getBooleanExtra(Keys.VIDEO, false);

        setTitle(name);


        if (!isVideo) {
            String[] urls = i.getStringArrayExtra(Keys.URL);
            populatePreviewImages(urls);

        } else {
            String url = i.getStringExtra(Keys.URL);
            populatePreviewVideos(url);
        }
    }

    private void populatePreviewImages(String[] urls) {
        ViewPager viewPagerImage = findViewById(R.id.view_pager_image);
        PreviewActivity.PreviewPagerAdapter previewPagerAdapter =
                new PreviewActivity.PreviewPagerAdapter(
                        getSupportFragmentManager(),
                        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerImage.setAdapter(previewPagerAdapter);

        if (viewPagerImage.getVisibility() != View.VISIBLE)
            viewPagerImage.setVisibility(View.VISIBLE);

//            ImageView image = findViewById(R.id.image);
//            image.setOnClickListener(view -> {
//                if (!hasFocus) {
//                    hideSystemUI();
//                } else {
//                    showSystemUI();
//                }
//                hasFocus = !hasFocus;
//            });
//            if (image.getVisibility() != View.VISIBLE)
//                image.setVisibility(View.VISIBLE);
//
//            Utils.setPicasso(url, image);

        List<Fragment> temp = new ArrayList<>();
        for (String url : urls) {
            PreviewItemFragment previewItemFragment = new PreviewItemFragment();
            previewItemFragment.setData(url, this);
            temp.add(previewItemFragment);
        }

        previewPages.clear();
        if (temp.size() > 0) {
            previewPages.addAll(temp);
            previewPagerAdapter.notifyDataSetChanged();
            viewPagerImage.setCurrentItem(0);
        }
    }

    private void populatePreviewVideos(String url) {
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


    /**
     * Interfaces callback ->
     * onImageClick
     */
    @Override
    public void onImageClick() {
        if (!hasFocus) {
            hideSystemUI();
        } else {
            showSystemUI();
        }
        hasFocus = !hasFocus;
    }


    /**
     * Private classes ->
     * PreviewPagerAdapter
     */

    private class PreviewPagerAdapter extends FragmentPagerAdapter {

        public PreviewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            return previewPages.get(position);
        }

        @Override
        public int getCount() {
            return previewPages.size();
        }
    }
}
