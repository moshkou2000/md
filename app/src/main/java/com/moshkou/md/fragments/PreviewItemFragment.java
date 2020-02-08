package com.moshkou.md.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.moshkou.md.R;
import com.moshkou.md.controls.ImageViewControl;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnImageListener;


public class PreviewItemFragment extends Fragment {

    private static final String TAG = "PREVIEW_FRG";


    private OnImageListener onImageListener;

    private ImageViewControl image;
    private String data = "";

    public PreviewItemFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_preview, container, false);

        image = view.findViewById(R.id.image);


        if (!image.hasOnClickListeners())
            image.setOnClickListener(v -> onImageListener.onImageClick());

        setData(data, onImageListener);

        return view;
    }


    public void setData(String data, OnImageListener onImageListener) {
        this.data = data;
        this.onImageListener = onImageListener;
        if (image != null && data.length() > 0) {
            Utils.setPicasso(data, image);
        }
    }
}
