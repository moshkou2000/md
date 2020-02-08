package com.moshkou.md.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.controls.AutoCompleteTextViewControl;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BillboardLocationModel;


public class LocationFragment extends Fragment {


    private static String TAG = "LOCATION_FRG";

    private AutoCompleteTextViewControl autoCompleteState;
    private AutoCompleteTextViewControl autoCompleteCity;
    private EditText textEditAddress;
    private EditText textEditPostcode;

    private BillboardLocationModel location = new BillboardLocationModel();
    private boolean isInitialized = false;
    private boolean is_new = false;

    public LocationFragment() { }




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

        autoCompleteCity = view.findViewById(R.id.auto_complete_city);
        autoCompleteState = view.findViewById(R.id.auto_complete_state);
        textEditAddress = view.findViewById(R.id.text_edit_address);
        textEditPostcode = view.findViewById(R.id.text_edit_postcode);

        init();
        populate();

        return view;
    }

    @Override
    public void onDestroy() {
        location = null;
        super.onDestroy();
    }




    /**
     * Init functions ->
     * init
     */

    private void init() {
        isInitialized = true;

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
     * setLocation
     * toggleView
     */

    private void populate() {
        if (isInitialized) {
            if (is_new) {
                autoCompleteState.setSelection(0);
                autoCompleteCity.setSelection(0);
                textEditAddress.setText("");
                textEditPostcode.setText("");

            } else {
                autoCompleteState.setText(location.state);
                autoCompleteCity.setText(location.city);
                textEditAddress.setText(location.address);
                textEditPostcode.setText(String.valueOf(location.postcode));
            }
        }
    }

    public void setLocation(BillboardLocationModel location, boolean is_new) {
        this.is_new = is_new;
        this.location = location;

        populate();
    }

    public BillboardLocationModel getLocation() {
        location.address = textEditAddress.getText().toString().trim();
        location.state = autoCompleteState.getText().toString().trim();
        location.city = autoCompleteCity.getText().toString().trim();
        location.postcode = textEditPostcode.getText().toString().trim();

        return location;
    }
}
