package com.moshkou.md.fragments;

import android.app.Dialog;
import android.content.Intent;
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
import com.moshkou.md.models.BillboardStatusModel;

import java.util.Objects;


public class StatusFragment extends Fragment implements
        OnAdapterListener {


    private static String TAG = "STATUS_FRG";

    private StatusAdapter adapter;

    private AutoCompleteTextView textEditStatus;
    private AutoCompleteTextView textEditReason;

    private GridView gridViewMedia;
    private Button buttonAdd;

    private BillboardStatusModel status = new BillboardStatusModel();
    private boolean isInitialized = false;
    private boolean is_new = false;
    private boolean is_deleted = true;

    public StatusFragment() { }




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
        buttonAdd = view.findViewById(R.id.button_add);

        init();
        populate();

        return view;
    }

    @Override
    public void onDestroy() {
        status = null;
        super.onDestroy();
    }




    /**
     * Init functions ->
     * init
     */

    private void init() {
        isInitialized = true;
        buttonAdd.setOnClickListener(v -> showDialogMediaPicker());
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
                textEditStatus.setText("");
                textEditReason.setText("");

                if (adapter != null)
                    adapter.clearItems();

            } else {
                textEditStatus.setText(status.status);
                textEditReason.setText(status.comment);

                if (adapter == null) {
                    adapter = new StatusAdapter(this, status.medias);
                    gridViewMedia.setAdapter(adapter);

                } else {
                    adapter.setItem(status.medias);
                }
            }
        }
    }

    public void setStatus(BillboardStatusModel status, boolean is_new) {
        this.is_new = is_new;
        this.status = status;

        populate();
    }

    private BillboardStatusModel getStatus() {
        String statuz = textEditStatus.getText().toString().trim();
        String reason = textEditReason.getText().toString().trim();

        BillboardStatusModel status = new BillboardStatusModel();
        status.status = statuz;
        status.comment = reason;

        return status;
    }




    /**
     * Alert ->
     * showDialogMediaPicker
     * showToast
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

                // status.medias.get(index)
            }
        }, 2400);
    }

    public void onDelete(int index) { }

    public void onUpdate(int index) { }

    public void onInteresting(int index, boolean isInteresting) { }
}
