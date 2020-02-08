package com.moshkou.md.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.activities.MediaActivity;
import com.moshkou.md.adapters.MediaAdapter;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.interfaces.OnAdapterListener;
import com.moshkou.md.models.BillboardMediaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MediaFragment extends Fragment implements
        OnAdapterListener {


    private static String TAG = "MEDIA_FRG";

    private MediaAdapter adapter;

    private GridView gridViewMedia;
    private Button buttonAdd;

    private List<BillboardMediaModel> medias = new ArrayList<>();
    private boolean isInitialized = false;
    private boolean is_new = false;
    private boolean is_deleted = true;

    public MediaFragment() {
        // Required empty public constructor
    }




    /**
     * Override functions ->
     * onCreate
     * onBackPressed
     **/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);

        gridViewMedia = view.findViewById(R.id.grid_view_media);
        buttonAdd = view.findViewById(R.id.button_add);

        init();
        populate();

        return view;
    }

    @Override
    public void onDestroy() {
        medias.clear();
        medias = null;
        super.onDestroy();
    }




    /**
     * Init functions ->
     * init
     */

    private void init() {
        isInitialized = true;

        buttonAdd.setOnClickListener(v -> {
            Intent i = new Intent(App.getContext(), MediaActivity.class);
            startActivity(i);
        });
    }




    /**
     * Helper functions ->
     * populate
     * setLocation
     * toggleView
     */

    private void populate() {
        if (isInitialized) {
            if (is_new) {
                Log.i(TAG, "media: null");
                if (adapter != null)
                    adapter.clearItems();

            } else {
                if (adapter == null) {
                    adapter = new MediaAdapter(this, medias);
                    gridViewMedia.setAdapter(adapter);

                } else {
                    Log.i(TAG, "media: " + medias.size());
                    adapter.setItem(medias);
                }
            }
        }
    }

    public void setMedias(List<BillboardMediaModel> medias, boolean is_new) {
        this.is_new = is_new;
        this.medias = medias;

        populate();
    }




    /**
     * Interfaces callback ->
     * onDeleteMedia
     * onDelete
     * onUpdate
     * onInteresting
     */

    @Override
    public void onDeleteMedia(final int index) {

        is_deleted = true;

        final View coordinatorLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.coordinatorLayout);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Media is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", (view1) -> {
                    is_deleted = false;
                    adapter.restoreItem();
                    Snackbar.make(coordinatorLayout, "Media is restored!", Snackbar.LENGTH_SHORT).show();
                });
        snackbar.setDuration(2000).show();

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (is_deleted) {
                // TODO: ***************************** actual deletion
                // delete if local OR call api if not

                // medias.get(index)
            }
        }, 2400);
    }

    public void onDelete(int index) { }

    public void onUpdate(int index) {
        Intent i = new Intent(App.getContext(), MediaActivity.class);
        i.putExtra(Keys.DATA, new Gson().toJson(medias.get(index), BillboardMediaModel.class));
        startActivity(i);
    }

    public void onInteresting(int index, boolean isInteresting) {

        // TODO: ***************************** service

        // medias.get(index)
    }
}
