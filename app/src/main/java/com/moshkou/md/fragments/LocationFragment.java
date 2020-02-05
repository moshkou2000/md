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
import android.widget.Toast;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.controls.AutoCompleteTextViewControl;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardLocationModel;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.KeyValue;


public class LocationFragment extends Fragment {


    private static String TAG = "LOCATION_FRG";

    private OnFragmentInteractionListener mListener;

    private LinearLayout layoutView;
    private LinearLayout layoutEdit;
    private LinearLayout layoutSave;

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

    private BillboardLocationModel location;
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
        buttonSave.setOnClickListener(v -> save());


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
            if (location == null) {
                toggleView(true);

                textAddress.setText("");
                textCity.setText("");
                textState.setText("");
                textPostcode.setText("");
                textCountry.setText("");
                autoCompleteState.setSelection(0);
                autoCompleteCity.setSelection(0);
                textEditAddress.setText("");
                textEditPostcode.setText("");

            } else if (location != null) {
                toggleView(false);

                textAddress.setText(location.address);
                textCity.setText(location.city);
                textState.setText(location.state);
                textPostcode.setText(String.valueOf(location.postcode));
                textCountry.setText(location.country);
                autoCompleteState.setText(location.state);
                autoCompleteCity.setText(location.city);
                textEditAddress.setText(location.address);
                textEditPostcode.setText(String.valueOf(location.postcode));
            }
        }
    }

    public void setLocation(BillboardLocationModel location) {
        this.location = location;

        populate();
    }

    private void toggleView(boolean isEdit) {
        if (isEdit) {
            layoutEdit.setVisibility(View.VISIBLE);
            layoutView.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
            layoutSave.setVisibility(View.VISIBLE);

        } else if (location != null) {
            layoutEdit.setVisibility(View.GONE);
            layoutView.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.VISIBLE);
            layoutSave.setVisibility(View.GONE);
        }
    }

    private void save() {
        String address = textEditAddress.getText().toString().trim();
        String state = autoCompleteState.getText().toString().trim();
        String city = autoCompleteCity.getText().toString().trim();
        String postcode = textEditPostcode.getText().toString().trim();

        if (address.isEmpty() || state.isEmpty() || city.isEmpty() || postcode.isEmpty()) {
            showToast(getString(R.string.message_error_required_fields));

        } else {
            textAddress.setText(address);
            textState.setText(state);
            textCity.setText(city);
            textPostcode.setText(postcode);

            if (location == null)
                location = new BillboardLocationModel();
            location.address = address;
            location.state = state;
            location.city = city;
            location.postcode = postcode;

            mListener.onLocationFragmentInteraction(location);
            toggleView(false);
        }
    }




    /**
     * Alert ->
     * showToast
     */

    private void showToast(String message) {
        Utils.toast(App.getContext(), Enumerates.Message.ERROR, message, Toast.LENGTH_LONG);
    }
}
