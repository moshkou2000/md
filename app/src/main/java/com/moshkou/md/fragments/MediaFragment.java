package com.moshkou.md.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.moshkou.md.R;
import com.moshkou.md.adapters.GalleryAdapter;
import com.moshkou.md.adapters.MediaAdapter;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardModel;


public class MediaFragment extends Fragment {


    private static String TAG = "MEDIA_FRG";

    private OnFragmentInteractionListener mListener;

    private MediaAdapter adapter;

    private GridView gridViewMedia;
    private Button buttonAdd;

    private BillboardModel selectedBillboard;
    private boolean isEdit = false;



    public MediaFragment() {
        // Required empty public constructor
    }

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
        super.onDetach();
        mListener = null;
    }

    public void setSelectedBillboard(BillboardModel selectedBillboard) {
        this.selectedBillboard = selectedBillboard;
    }

    private void init() {
        adapter = new MediaAdapter(getActivity(), selectedBillboard);
        gridViewMedia.setAdapter(adapter);

        buttonAdd.setOnClickListener(v -> {
            // TODO: open add media activity

        });
    }

    private void populate() {
        if (selectedBillboard != null && selectedBillboard.medias != null) {
            // TODO: populate gridViewMedia
        }
    }
}
