package com.moshkou.md.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;

import com.moshkou.md.R;
import com.moshkou.md.adapters.MediaAdapter;
import com.moshkou.md.adapters.StatusAdapter;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardModel;


public class StatusFragment extends Fragment {


    private static String TAG = "STATUS_FRG";

    private OnFragmentInteractionListener mListener;

    private StatusAdapter adapter;

    private AutoCompleteTextView textEditCity;
    private AutoCompleteTextView textEditState;

    private GridView gridViewMedia;
    private Button buttonStatusCancel;
    private Button buttonStatusDone;
    private Button buttonReasonCancel;
    private Button buttonReasonDone;

    private Button buttonAdd;

    private BillboardModel selectedBillboard;
    private boolean isEdit = false;



    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        gridViewMedia = view.findViewById(R.id.grid_view_media);

        buttonStatusCancel = view.findViewById(R.id.button_cancel);
        buttonStatusDone = view.findViewById(R.id.button_done);
        buttonReasonCancel = view.findViewById(R.id.button_reason_cancel);
        buttonReasonDone = view.findViewById(R.id.button_reason_done);
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
        adapter = new StatusAdapter(getActivity(), selectedBillboard);
        gridViewMedia.setAdapter(adapter);

        buttonStatusDone.setOnClickListener(v -> {
            // TODO: open add media activity
        });
        buttonStatusCancel.setOnClickListener(v -> {
            // TODO: open add media activity
        });

        buttonReasonDone.setOnClickListener(v -> {
            // TODO: open add media activity
        });
        buttonReasonCancel.setOnClickListener(v -> {
            // TODO: open add media activity
        });

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
