package com.moshkou.md.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.activities.BillboardActivity;
import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.controls.AutoCompleteTextViewControl;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.services.Billboards;

import java.util.Objects;


public class BillboardFragment extends Fragment {


    private static String TAG = "BILLBOARD_FRG";

    private OnFragmentInteractionListener mListener;

    private LinearLayout layoutView;
    private LinearLayout layoutEdit;
    private LinearLayout layoutSave;

    private TextView textMediaOwner;
    private TextView textFormat;
    private TextView textEnvironment;
    private TextView textLighting;
    private TextView textNoOfPanels;
    private TextView textSpeedLimit;

    private AutoCompleteTextViewControl autoCompleteMediaOwner;
    private AutoCompleteTextViewControl autoCompleteFormat;
    private AutoCompleteTextViewControl autoCompleteEnvironment;
    private CheckBox checkboxDigitalScreen;
    private CheckBox checkboxLighting;
    private RadioGroup radioGroupNoPanels;
    private RadioGroup radioGroupSpeedLimit;

    private Button buttonEdit;
    private Button buttonCancel;
    private Button buttonSave;

    private BillboardModel billboard;
    private boolean isInitialized = false;

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

        layoutView = view.findViewById(R.id.layout_view);
        layoutEdit = view.findViewById(R.id.layout_edit);
        layoutSave = view.findViewById(R.id.layout_save);

        textMediaOwner = view.findViewById(R.id.text_media_owner);
        textFormat = view.findViewById(R.id.text_format);
        textEnvironment = view.findViewById(R.id.text_environment);
        textLighting = view.findViewById(R.id.text_lighting);
        textNoOfPanels = view.findViewById(R.id.text_no_of_panels);
        textSpeedLimit = view.findViewById(R.id.text_speed_limit);

        autoCompleteMediaOwner = view.findViewById(R.id.text_edit_media_owner);
        autoCompleteFormat = view.findViewById(R.id.text_edit_format);
        autoCompleteEnvironment = view.findViewById(R.id.text_edit_environment);
        checkboxDigitalScreen = view.findViewById(R.id.checkbox_digital_screen);
        checkboxLighting = view.findViewById(R.id.checkbox_lighting);
        radioGroupNoPanels = view.findViewById(R.id.radio_group_no_panels);
        radioGroupSpeedLimit = view.findViewById(R.id.radio_group_speed_limit);

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
            if (billboard == null) {
                toggleView(true);

                textMediaOwner.setText("");
                textFormat.setText("");
                textEnvironment.setText("");
                textLighting.setText("No");
                textNoOfPanels.setText("1");
                textSpeedLimit.setText("<30");
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
                toggleView(false);

                textMediaOwner.setText(billboard.media_owner);
                textFormat.setText(billboard.format);
                textEnvironment.setText(billboard.environment);
                textLighting.setText(billboard.lighting ? "Yes" : "No");
                textNoOfPanels.setText(String.valueOf(billboard.no_panels));
                textSpeedLimit.setText(billboard.speed_limit);

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

    public void setBillboard(BillboardModel billboard) {
        this.billboard = billboard;

        Log.i(TAG, "select");
        populate();
    }

    private void toggleView(boolean isEdit) {
        if (isEdit) {
            layoutEdit.setVisibility(View.VISIBLE);
            layoutView.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
            layoutSave.setVisibility(View.VISIBLE);

        } else if (billboard != null) {
            layoutEdit.setVisibility(View.GONE);
            layoutView.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.VISIBLE);
            layoutSave.setVisibility(View.GONE);
        }
    }

    private void save() {
        RadioButton radioButtonNoPanels = Objects.requireNonNull(getActivity())
                .findViewById(radioGroupNoPanels.getCheckedRadioButtonId());

        RadioButton radioButtonSpeedLimit = Objects.requireNonNull(getActivity())
                .findViewById(radioGroupSpeedLimit.getCheckedRadioButtonId());

        String media_owner = autoCompleteMediaOwner.getText().toString().trim();
        String format = autoCompleteFormat.getText().toString().trim();
        String environment = autoCompleteEnvironment.getText().toString().trim();
        String type = checkboxDigitalScreen.isChecked() ? Flags.DIGITAL : Flags.STATIC;
        boolean lighting = checkboxLighting.isChecked();
        Integer no_panels = Integer.valueOf(radioButtonNoPanels.getText().toString());
        String speed_limit = radioButtonSpeedLimit.getText().toString();

        if (media_owner.isEmpty() || format.isEmpty() || environment.isEmpty()) {
            showToast(getString(R.string.message_error_required_fields));

        } else {
            textMediaOwner.setText(media_owner);
            textFormat.setText(format);
            textEnvironment.setText(environment);
            textLighting.setText(billboard.lighting ? "Yes" : "No");
            textNoOfPanels.setText(String.valueOf(billboard.no_panels));
            textSpeedLimit.setText(speed_limit);

            if (billboard == null)
                billboard = new BillboardModel();
            billboard.media_owner = media_owner;
            billboard.format = format;
            billboard.environment = environment;
            billboard.type = type;
            billboard.lighting = lighting;
            billboard.no_panels = no_panels;
            billboard.speed_limit = speed_limit;
        }

        mListener.onBillboardFragmentInteraction(billboard);
        toggleView(false);
    }




    /**
     * Alert ->
     * showToast
     */

    private void showToast(String message) {
        Utils.toast(App.getContext(), Enumerates.Message.ERROR, message, Toast.LENGTH_LONG);
    }
}
