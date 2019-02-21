package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.moshkou.md.fragments.HorizontalListsFragment;
import com.moshkou.md.fragments.GalleryFragment;
import com.moshkou.md.fragments.ListMultiItemTypeFragment;
import com.moshkou.md.R;


public class BottomNavigationActivity extends AppCompatActivity implements
        GalleryFragment.OnFragmentInteractionListener,
        HorizontalListsFragment.OnFragmentInteractionListener,
        ListMultiItemTypeFragment.OnFragmentInteractionListener {


    private final Context context = this;

    private BottomNavigationView navigation;
    private FrameLayout fragmentContainer;

    private GalleryFragment galleryFragment;
    private HorizontalListsFragment horizontalListsFragment;
    private ListMultiItemTypeFragment listMultiItemTypeFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_camera:
                    startActivity(new Intent(context, CameraActivity.class));
                    return true;
                case R.id.navigation_search:
                    transaction.replace(R.id.fragment_container, horizontalListsFragment, "horizontalListsFragment");
                    transaction.addToBackStack("horizontalListsFragment");
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.fragment_container, galleryFragment, "galleryFragment");
                    transaction.addToBackStack("galleryFragment");
                    transaction.commit();

                    return true;
                case R.id.navigation_emergency:
                    transaction.replace(R.id.fragment_container, listMultiItemTypeFragment, "listMultiItemTypeFragment");
                    transaction.addToBackStack("listMultiItemTypeFragment");
                    transaction.commit();
                    return true;
                case R.id.navigation_person:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        fragmentContainer = findViewById(R.id.fragment_container);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragments();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, galleryFragment, "galleryFragment");
        transaction.addToBackStack("galleryFragment");
        transaction.commit();

        navigation.setSelectedItemId(R.id.navigation_dashboard);

        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        galleryFragment = null;
        horizontalListsFragment = null;
        listMultiItemTypeFragment = null;
        Runtime.getRuntime().gc();
    }


    private void initFragments() {
        galleryFragment = new GalleryFragment();
        horizontalListsFragment = new HorizontalListsFragment();
        listMultiItemTypeFragment = new ListMultiItemTypeFragment();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
