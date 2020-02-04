package com.moshkou.md.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.controls.AutoCompleteTextViewControl;
import com.moshkou.md.helpers.Utils;
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

    private AutoCompleteTextViewControl autoCompleteState;
    private AutoCompleteTextViewControl autoCompleteCity;
    private EditText textEditAddress;
    private EditText textEditPostcode;

    private Button buttonEdit;
    private Button buttonCancel;
    private Button buttonSave;

    private BillboardModel selectedBillboard;
    private boolean isInitialized = false;

    public LocationFragment() {
        // Required empty public constructor
    }




    /**
     * Override functions ->
     * onCreate
     * onCreateView
     * onAttach
     * onDetach
     **/

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
        textState = view.findViewById(R.id.text_state);
        textCity = view.findViewById(R.id.text_city);
        textPostcode = view.findViewById(R.id.text_postcode);
        textCountry = view.findViewById(R.id.text_country);

        autoCompleteCity = view.findViewById(R.id.auto_complete_city);
        autoCompleteState = view.findViewById(R.id.auto_complete_state);
        textEditAddress = view.findViewById(R.id.text_edit_address);
        textEditPostcode = view.findViewById(R.id.text_edit_postcode);
        buttonEdit = view.findViewById(R.id.button_edit);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonSave = view.findViewById(R.id.button_save);

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

        buttonEdit.setOnClickListener(v -> toggleView(true));
        buttonCancel.setOnClickListener(v -> toggleView(false));
        buttonSave.setOnClickListener(v -> {
            // TODO: api call

            toggleView(false);
        });


        // autoComplete state
        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.STATES);
        autoCompleteState.setAdapter(adapterState);
        autoCompleteState.setThreshold(0);
        autoCompleteState.setOnItemClickListener((parent, v, position, id) -> {

            // autoComplete city
            ArrayAdapter<String> adapterCity = new ArrayAdapter<>(
                    App.getContext(),
                    android.R.layout.select_dialog_item,
                    Utils.getCities(adapterState.getItem(position)));
            autoCompleteCity.setAdapter(adapterCity);
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
            if (selectedBillboard == null) {
                toggleView(true);

                textName.setText("");
                textAddress.setText("");
                textCity.setText("");
                textState.setText("");
                textPostcode.setText("");
                textCountry.setText("");
                autoCompleteState.setSelection(0);
                autoCompleteCity.setSelection(0);
                textEditAddress.setText("");
                textEditPostcode.setText("");

            } else if (selectedBillboard.location != null) {
                toggleView(false);

                textName.setText(selectedBillboard.location.name);
                textAddress.setText(selectedBillboard.location.address);
                textCity.setText(selectedBillboard.location.city);
                textState.setText(selectedBillboard.location.state);
                textPostcode.setText(String.valueOf(selectedBillboard.location.postcode));
                textCountry.setText(selectedBillboard.location.country);
                autoCompleteState.setText(selectedBillboard.location.state);
                autoCompleteCity.setText(selectedBillboard.location.city);
                textEditAddress.setText(selectedBillboard.location.address);
                textEditPostcode.setText(String.valueOf(selectedBillboard.location.postcode));
            }
        }
    }

    public void setSelectedBillboard(BillboardModel selectedBillboard) {
        this.selectedBillboard = selectedBillboard;

        populate();
    }

    private void toggleView(boolean isEdit) {
        if (isEdit) {
            layoutEdit.setVisibility(View.VISIBLE);
            layoutView.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
            layoutSave.setVisibility(View.VISIBLE);

        } else if (selectedBillboard != null) {
            layoutEdit.setVisibility(View.GONE);
            layoutView.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.VISIBLE);
            layoutSave.setVisibility(View.GONE);
        }
    }
}
