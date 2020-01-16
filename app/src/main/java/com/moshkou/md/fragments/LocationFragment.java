package com.moshkou.md.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardModel;


public class LocationFragment extends Fragment {


    private static String TAG = "LOCATION_FRG";

    private OnFragmentInteractionListener mListener;

    private LinearLayout layoutView;
    private LinearLayout layoutEdit;
    private LinearLayout layoutSave;

    private TextView textName;
    private TextView textAddress;
    private TextView textCity;
    private TextView textState;
    private TextView textPostcode;
    private TextView textCountry;

    private TextView textEditCity;
    private TextView textEditState;
    private EditText textEditAddress;
    private EditText textEditPostcode;

    private Button buttonEdit;
    private Button buttonCancel;
    private Button buttonSave;

    private BillboardModel selectedBillboard;
    private boolean isEdit = false;



    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        layoutView = view.findViewById(R.id.layout_view);
        layoutEdit = view.findViewById(R.id.layout_edit);
        layoutSave = view.findViewById(R.id.layout_save);

        textName = view.findViewById(R.id.text_name);
        textAddress = view.findViewById(R.id.text_address);
        textCity = view.findViewById(R.id.text_city);
        textState = view.findViewById(R.id.text_state);
        textPostcode = view.findViewById(R.id.text_postcode);
        textCountry = view.findViewById(R.id.text_country);

        textEditCity = view.findViewById(R.id.text_edit_city);
        textEditState = view.findViewById(R.id.text_edit_state);
        textEditAddress = view.findViewById(R.id.text_edit_address);
        textEditPostcode = view.findViewById(R.id.text_edit_postcode);

        buttonEdit = view.findViewById(R.id.button_edit);
        buttonEdit.setOnClickListener(v -> toggleView());

        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(v -> toggleView());

        buttonSave = view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(v -> {
            // TODO: api call

            toggleView();
        });

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

    private void toggleView() {
        if (isEdit) {
            layoutEdit.setVisibility(View.GONE);
            layoutView.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.VISIBLE);
            layoutSave.setVisibility(View.GONE);
        } else {
            layoutEdit.setVisibility(View.VISIBLE);
            layoutView.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
            layoutSave.setVisibility(View.VISIBLE);
        }

        isEdit = !isEdit;
    }

    private void populate() {
        if (selectedBillboard != null && selectedBillboard.location != null) {
            textName.setText(selectedBillboard.location.name);
            textAddress.setText(selectedBillboard.location.address);
            textCity.setText(selectedBillboard.location.city);
            textState.setText(selectedBillboard.location.state);
            textPostcode.setText(String.valueOf(selectedBillboard.location.postcode));
            textCountry.setText(selectedBillboard.location.country);

            textEditCity.setText(selectedBillboard.location.city);
            textEditState.setText(selectedBillboard.location.state);
            textEditAddress.setText(selectedBillboard.location.address);
            textEditPostcode.setText(String.valueOf(selectedBillboard.location.postcode));
        }
    }
}
