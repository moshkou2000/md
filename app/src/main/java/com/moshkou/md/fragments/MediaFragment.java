package com.moshkou.md.fragments;

import android.content.Context;
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
import com.google.gson.reflect.TypeToken;
import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.activities.MediaActivity;
import com.moshkou.md.adapters.MediaAdapter;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.interfaces.OnAdapterListener;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardMediaModel;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;


public class MediaFragment extends Fragment implements
        OnAdapterListener {


    private static String TAG = "MEDIA_FRG";

    private OnFragmentInteractionListener mListener;

    private MediaAdapter adapter;

    private GridView gridViewMedia;
    private Button buttonAdd;

    private List<BillboardMediaModel> medias;
    private boolean isInitialized = false;
    boolean toBeRemoved = true;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
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
     * setSelectedBillboard
     * toggleView
     */

    private void populate() {
        if (isInitialized) {
            if (medias == null) {
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

    public void setSelectedBillboard(List<BillboardMediaModel> medias) {
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

        toBeRemoved = true;

        final View coordinatorLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.coordinatorLayout);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Media is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", (view1) -> {
                    toBeRemoved = false;
                    Snackbar.make(coordinatorLayout, "Media is restored!", Snackbar.LENGTH_SHORT).show();
                });
        snackbar.show();

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (!toBeRemoved)
                adapter.restoreItem();


            // TODO: ***************************** actual deletion
            // delete if local OR call api if not

        }, 2000);
    }

    @Override
    public void onDelete(int index) { }

    @Override
    public void onUpdate(int index) {
        Intent i = new Intent(App.getContext(), MediaActivity.class);
        i.putExtra(Keys.DATA, new Gson().toJson(medias.get(index), BillboardMediaModel.class));
        startActivity(i);
    }

    @Override
    public void onInteresting(int index, boolean isInteresting) { }
}
