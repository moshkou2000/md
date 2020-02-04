package com.moshkou.md.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.material.snackbar.Snackbar;
import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.activities.CameraActivity;
import com.moshkou.md.adapters.StatusAdapter;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnAdapterListener;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardModel;

import java.util.List;
import java.util.Objects;


public class StatusFragment extends Fragment implements
        OnAdapterListener {


    private static String TAG = "STATUS_FRG";

    private OnFragmentInteractionListener mListener;

    private StatusAdapter adapter;

    private AutoCompleteTextView textEditStatus;
    private AutoCompleteTextView textEditReason;

    private GridView gridViewMedia;
    private Button buttonCancel;
    private Button buttonSave;
    private Button buttonAdd;

    private BillboardModel selectedBillboard;
    private List<String> files;
    private boolean isInitialized = false;
    boolean toBeRemoved = true;

    public StatusFragment() {
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
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        textEditStatus = view.findViewById(R.id.text_edit_status);
        textEditReason = view.findViewById(R.id.text_edit_reason);
        gridViewMedia = view.findViewById(R.id.grid_view_media);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonSave = view.findViewById(R.id.button_save);
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

        buttonSave.setOnClickListener(v -> {
            // TODO: open add media activity
        });
        buttonCancel.setOnClickListener(v -> {
            // TODO: open add media activity
        });

        buttonAdd.setOnClickListener(v -> showDialogMediaPicker());
    }




    /**
     * Helper functions ->
     * populate
     * setSelectedBillboard
     * toggleView
     */

    private void populate() {
        if (isInitialized) {
            if (selectedBillboard != null) {
                textEditStatus.setText(selectedBillboard.status.status);
                textEditReason.setText(selectedBillboard.status.comment);

                if (adapter == null) {
                    adapter = new StatusAdapter(this, selectedBillboard.status.files);
                    gridViewMedia.setAdapter(adapter);

                } else {
                    adapter.setItem(files);
                }

            } else if (files != null) {
                if (adapter == null) {
                    adapter = new StatusAdapter(this, files);
                    gridViewMedia.setAdapter(adapter);

                } else {
                    adapter.setItem(files);
                }

            } else {
                textEditStatus.setText("");
                textEditReason.setText("");

                if (adapter != null)
                    adapter.clearItems();
            }
        }
    }

    public void setSelectedBillboard(BillboardModel selectedBillboard, List<String> files) {
        this.selectedBillboard = selectedBillboard;
        this.files = files;

        populate();
    }




    /**
     * Alert ->
     * showDialogMediaPicker
     * showSnackBar
     */

    private void showDialogMediaPicker() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_media_picker);

        Button buttonCamera = dialog.findViewById(R.id.btn_camera);
        Button buttonGallery = dialog.findViewById(R.id.btn_gallery);


        buttonCamera.setOnClickListener(v -> {
            Settings.MEDIA_PICKER = Enumerates.MediaPicker.STATUS_FRAGMENT;
            startActivity(new Intent(App.getContext(), CameraActivity.class));
            dialog.dismiss();
        });
        buttonGallery.setOnClickListener(v -> {
            Settings.MEDIA_PICKER = Enumerates.MediaPicker.STATUS_FRAGMENT;
            Utils.openDefaultGalleryApp(Objects.requireNonNull(getActivity()));
            dialog.dismiss();
        });

        dialog.show();
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
    public void onUpdate(int index) { }

    @Override
    public void onInteresting(int index, boolean isInteresting) { }
}
