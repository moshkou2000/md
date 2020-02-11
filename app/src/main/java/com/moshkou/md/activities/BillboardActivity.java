package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.fragments.BillboardFragment;
import com.moshkou.md.fragments.LocationFragment;
import com.moshkou.md.fragments.MediaFragment;
import com.moshkou.md.fragments.StatusFragment;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnBillboardsListener;
import com.moshkou.md.models.BillboardLocationModel;
import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.BillboardStatusModel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;


public class BillboardActivity extends AppCompatActivity implements
        OnBillboardsListener {


    private static String TAG = "BILLBOARD";


    private final Context context = this;

    private TabLayout tabs;
    private ViewPager pager;

    private LocationFragment locationFragment;
    private BillboardFragment billboardFragment;
    private MediaFragment mediaFragment;
    private StatusFragment statusFragment;

    private static final int[] TAB_TITLES = new int[] { R.string.placeholder_location, R.string.placeholder_billboards, R.string.placeholder_media, R.string.placeholder_status };

    private BillboardModel selectedBillboard = new BillboardModel();




    /**
     * Override functions ->
     * onCreate
     * onActivityResult
     * onDestroy
     * onNewIntent
     * onBackPressed
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);

        getExtra();
        init();
        populate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            try {
                File dst = new File(Settings.APP_PICTURE_DIRECTORY, Utils.makeFileName(".png"));
                String mediaPath = Utils.copyTo(data.getData(), dst);

                selectedBillboard.status.medias.add(mediaPath);
                statusFragment.setStatus(selectedBillboard.status, selectedBillboard.is_new);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationFragment = null;
        billboardFragment = null;
        mediaFragment = null;
        statusFragment = null;
        selectedBillboard = null;
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
        getExtra();
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }




    /**
     * Init functions ->
     * init
     * initToolbar
     */

    private void init() {
        locationFragment = new LocationFragment();
        billboardFragment = new BillboardFragment();
        mediaFragment = new MediaFragment();
        statusFragment = new StatusFragment();

        BillboardActivity.PagerAdapter pagerAdapter =
                new BillboardActivity.PagerAdapter(
                        getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(4);
        tabs.setupWithViewPager(pager);

        if (selectedBillboard.is_new)
            tabs.getTabAt(0).select();
        else
            tabs.getTabAt(2).select();

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_billboard);
        toolbar.inflateMenu(R.menu.filter);
        toolbar.setNavigationOnClickListener(view -> backPressed());
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem.getItemId()));
    }




    /**
     * Helper functions ->
     * getExtra
     * backPressed
     */

    private void getExtra() {
        Intent intent = getIntent();

        String type = intent.getStringExtra(Keys.TYPE);

        Log.i(TAG, "type: " + type);

        if (type != null) {
            if (type.equals(Keys.BILLBOARD)) {
                String data = intent.getStringExtra(Keys.DATA);
                Type t = new TypeToken<BillboardModel>(){}.getType();
                selectedBillboard = new Gson().fromJson(data, t);

            } else if (type.equals(Flags.MEDIA)) { // from MediaActivity
                String data = intent.getStringExtra(Keys.DATA);
                Type t = new TypeToken<BillboardMediaModel>(){}.getType();
                BillboardMediaModel media = new Gson().fromJson(data, t);

                if (media != null) {
                    selectedBillboard.medias.add(media);
                    mediaFragment.setMedias(selectedBillboard.medias, selectedBillboard.is_new);
                }

            } else if (type.equals(Keys.URI)) { // from CameraActivity
                Uri uri = intent.getParcelableExtra(Keys.URI);

                if (uri != null) {
                    selectedBillboard.status.medias.add(uri.getPath());
                    statusFragment.setStatus(selectedBillboard.status, selectedBillboard.is_new);
                }
            }
        } // END_OF_TYPE
    }

    private void clear() {
        // TODO: cancel

        backPressed();
    }

    private void done() {
        // TODO: save into local storage

        backPressed();
    }

    private boolean onMenuItemClick(int itemId) {
        switch (itemId) {
            case R.id.action1:
                clear();
                break;
            case R.id.action2:
                done();
                break;
            default:
                break;
        }

        return true;
    }

    private void backPressed() {
        Utils.hideKeyboard(this);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra(Keys.TYPE, Keys.FILTER);
            Type type = new TypeToken<BillboardModel>(){}.getType();
            i.putExtra(Keys.DATA, new Gson().toJson(selectedBillboard, type));
            startActivity(i);
            finish();
        }, 200);
    }




    /**
     * Populate layouts ->
     * populate
     */

    private void populate() {
        locationFragment.setLocation(selectedBillboard.location, selectedBillboard.is_new);
        billboardFragment.setBillboard(selectedBillboard, selectedBillboard.is_new);
        mediaFragment.setMedias(selectedBillboard.medias, selectedBillboard.is_new);
        statusFragment.setStatus(selectedBillboard.status, selectedBillboard.is_new);
    }




    /**
     * API callback ->
     * onCreateBillboard
     * onGetBillboards
     * onCreateBillboardLocation
     * onUpdateBillboardLocation
     * onCreateBillboardInfo
     * onUpdateBillboardInfo
     * onCreateBillboardMedia
     * onUpdateBillboardMedia
     * onCreateBillboardStatus
     * onUpdateBillboardStatus
     */

    public void onCreateBillboard(BillboardModel billboard) { }

    public void onGetBillboards(List<BillboardModel> billboards) { }

    public void onCreateBillboardLocation(BillboardLocationModel location) { }

    public void onUpdateBillboardLocation(BillboardLocationModel location) { }

    public void onCreateBillboardInfo(BillboardModel billboard) { }

    public void onUpdateBillboardInfo(BillboardModel billboard) { }

    public void onCreateBillboardMedia(BillboardMediaModel media) { }

    public void onUpdateBillboardMedia(BillboardMediaModel media) { }

    public void onCreateBillboardStatus(BillboardStatusModel status) { }

    public void onUpdateBillboardStatus(BillboardStatusModel status) { }




    /**
     * Alert ->
     * showToast
     * showSnackBar
     */

    private void showToast(String message) {
        Utils.toast(App.getContext(), Enumerates.Message.ERROR, message, Toast.LENGTH_LONG);
    }

    public void showSnackBar() {
        final View coordinatorLayout = findViewById(R.id.coordinatorLayout);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Media is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", (view1) -> Snackbar.make(coordinatorLayout, "Media is restored!", Snackbar.LENGTH_SHORT).show());
        snackbar.show();
    }




    /**
     * Private classes ->
     * InfoPagerAdapter
     */

    private class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return locationFragment;
                case 1:
                    return billboardFragment;
                case 2:
                    return mediaFragment;
                case 3:
                    return statusFragment;
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
