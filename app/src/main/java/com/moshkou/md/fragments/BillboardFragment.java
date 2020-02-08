package com.moshkou.md.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.controls.AutoCompleteTextViewControl;
import com.moshkou.md.models.BillboardModel;

import java.util.Objects;


public class BillboardFragment extends Fragment {


    private static String TAG = "BILLBOARD_FRG";

    private AutoCompleteTextViewControl autoCompleteMediaOwner;
    private AutoCompleteTextViewControl autoCompleteFormat;
    private AutoCompleteTextViewControl autoCompleteEnvironment;
    private CheckBox checkboxDigitalScreen;
    private CheckBox checkboxLighting;
    private RadioGroup radioGroupNoPanels;
    private RadioGroup radioGroupSpeedLimit;

    private BillboardModel billboard = new BillboardModel();
    private boolean isInitialized = false;
    private boolean is_new = false;

    public BillboardFragment() {
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
        View view = inflater.inflate(R.layout.fragment_billboard, container, false);

        autoCompleteMediaOwner = view.findViewById(R.id.text_edit_media_owner);
        autoCompleteFormat = view.findViewById(R.id.text_edit_format);
        autoCompleteEnvironment = view.findViewById(R.id.text_edit_environment);
        checkboxDigitalScreen = view.findViewById(R.id.checkbox_digital_screen);
        checkboxLighting = view.findViewById(R.id.checkbox_lighting);
        radioGroupNoPanels = view.findViewById(R.id.radio_group_no_panels);
        radioGroupSpeedLimit = view.findViewById(R.id.radio_group_speed_limit);

        init();
        populate();

        return view;
    }

    @Override
    public void onDestroy() {
        billboard = null;
        super.onDestroy();
    }

    /**
     * Init functions ->
     * init
     */

    private void init() {
        isInitialized = true;
        // autoComplete mediaOwner
        ArrayAdapter<String> adapterMediaOwner = new ArrayAdapter<>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.MEDIA_OWNER);
        autoCompleteMediaOwner.setAdapter(adapterMediaOwner);

        // autoComplete format
        ArrayAdapter<String> adapterFormat = new ArrayAdapter<String>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.FORMAT);
        autoCompleteFormat.setAdapter(adapterFormat);

        // autoComplete environment
        ArrayAdapter<String> adapterEnvironment = new ArrayAdapter<String>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.ENVIRONMENT);
        autoCompleteEnvironment.setAdapter(adapterEnvironment);
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
                autoCompleteMediaOwner.setText("");
                autoCompleteFormat.setSelection(0);
                autoCompleteEnvironment.setSelection(0);
                checkboxDigitalScreen.setChecked(false);
                checkboxLighting.setChecked(false);
                ((RadioButton) radioGroupNoPanels.getChildAt(0))
                        .setChecked(true);
                ((RadioButton) radioGroupSpeedLimit.getChildAt(0))
                        .setChecked(true);

            } else {
                autoCompleteMediaOwner.setText(billboard.media_owner);
                autoCompleteFormat.setText(billboard.format);
                autoCompleteEnvironment.setText(billboard.environment);
                checkboxDigitalScreen.setChecked(billboard.type.equals(Flags.DIGITAL));
                checkboxLighting.setChecked(billboard.lighting);
                ((RadioButton) radioGroupNoPanels.getChildAt(billboard.getNoPanelsIndex()))
                        .setChecked(true);
                ((RadioButton) radioGroupSpeedLimit.getChildAt(billboard.getSpeedLimitIndex()))
                        .setChecked(true);
            }
        }
    }

    public void setBillboard(BillboardModel billboard, boolean is_new) {
        this.is_new = is_new;
        this.billboard = billboard;

        populate();
    }

    public BillboardModel getBillboard() {
        RadioButton radioButtonNoPanels = Objects.requireNonNull(getActivity())
                .findViewById(radioGroupNoPanels.getCheckedRadioButtonId());

        RadioButton radioButtonSpeedLimit = Objects.requireNonNull(getActivity())
                .findViewById(radioGroupSpeedLimit.getCheckedRadioButtonId());

        billboard.media_owner = autoCompleteMediaOwner.getText().toString().trim();
        billboard.format = autoCompleteFormat.getText().toString().trim();
        billboard.environment = autoCompleteEnvironment.getText().toString().trim();
        billboard.type = checkboxDigitalScreen.isChecked() ? Flags.DIGITAL : Flags.STATIC;
        billboard.lighting = checkboxLighting.isChecked();
        billboard.no_panels = Integer.valueOf(radioButtonNoPanels.getText().toString());
        billboard.speed_limit = radioButtonSpeedLimit.getText().toString();

        return billboard;
    }
}
