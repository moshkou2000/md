package com.moshkou.md.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.moshkou.md.R;
import com.moshkou.md.adapters.MediaAdapter;
import com.moshkou.md.adapters.StatusAdapter;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardModel;


public class StatusFragment extends Fragment {


    private static String TAG = "STATUS_FRG";

    private OnFragmentInteractionListener mListener;

    private StatusAdapter adapter;

    private AutoCompleteTextView textEditStatus;
    private AutoCompleteTextView textEditReason;

    private LinearLayout layoutButtons;
    private GridView gridViewMedia;
    private Button buttonCancel;
    private Button buttonDone;
    private Button buttonAdd;

    private BillboardModel selectedBillboard;
    private boolean isInitialized = false;



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

        layoutButtons = view.findViewById(R.id.layout_buttons);
        textEditStatus = view.findViewById(R.id.text_edit_status);
        textEditReason = view.findViewById(R.id.text_edit_reason);
        gridViewMedia = view.findViewById(R.id.grid_view_media);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonDone = view.findViewById(R.id.button_done);
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

        populate();
    }

    private void init() {
        isInitialized = true;

        textEditStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String status = s.toString().trim();
                if (selectedBillboard != null && !status.equals(selectedBillboard.status.status))
                    toggleButtons(true);
            }
        });
        textEditReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String status = s.toString().trim();
                if (selectedBillboard != null && !status.equals(selectedBillboard.status.comment))
                    toggleButtons(true);
            }
        });

        buttonDone.setOnClickListener(v -> {
            // TODO: open add media activity
        });
        buttonCancel.setOnClickListener(v -> {
            // TODO: open add media activity
        });

        buttonAdd.setOnClickListener(v -> {
            // TODO: open add media activity
        });
    }

    private void populate() {
        if (isInitialized) {
            if (selectedBillboard == null) {
                toggleButtons(false);

                textEditStatus.setText("");
                textEditReason.setText("");

                if (adapter != null)
                    adapter.clearItems();

            } else if (selectedBillboard.status != null) {
                toggleButtons(true);

                textEditStatus.setText(selectedBillboard.status.status);
                textEditReason.setText(selectedBillboard.status.comment);

                adapter = new StatusAdapter(getActivity(), selectedBillboard);
                gridViewMedia.setAdapter(adapter);
            }
        }
    }

    private void toggleButtons(boolean isEdit) {
        if (isEdit) {
            layoutButtons.setVisibility(View.VISIBLE);
            buttonAdd.setVisibility(View.GONE);
        } else {
            layoutButtons.setVisibility(View.GONE);
            buttonAdd.setVisibility(View.VISIBLE);
        }
    }
}
