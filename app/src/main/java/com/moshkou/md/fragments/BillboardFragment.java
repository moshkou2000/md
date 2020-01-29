package com.moshkou.md.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardModel;


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

    private Spinner spinnerMediaOwner;
    private Spinner spinnerFormat;
    private Spinner spinnerEnvironment;
    private CheckBox checkboxDigitalScreen;
    private CheckBox checkboxLighting;
    private RadioGroup radioGroupNoPanels;
    private RadioGroup radioGroupSpeedLimit;

    private Button buttonEdit;
    private Button buttonCancel;
    private Button buttonSave;

    private BillboardModel selectedBillboard;
    private boolean isInitialized = false;



    public BillboardFragment() {
        // Required empty public constructor
    }

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

        spinnerMediaOwner = view.findViewById(R.id.text_edit_media_owner);
        spinnerFormat = view.findViewById(R.id.text_edit_format);
        spinnerEnvironment = view.findViewById(R.id.text_edit_environment);
        checkboxDigitalScreen = view.findViewById(R.id.checkbox_digital_screen);
        checkboxLighting = view.findViewById(R.id.checkbox_lighting);
        radioGroupNoPanels = view.findViewById(R.id.radio_group_no_panels);
        radioGroupSpeedLimit = view.findViewById(R.id.radio_group_speed_limit);

        buttonEdit = view.findViewById(R.id.button_edit);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonSave = view.findViewById(R.id.button_save);

        Log.i(TAG, "init");

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

        Log.i(TAG, "select");
        populate();
    }

    private void init() {
        isInitialized = true;

        buttonEdit.setOnClickListener(v -> toggleView(true));
        buttonCancel.setOnClickListener(v -> toggleView(false));
        buttonSave.setOnClickListener(v -> {
            // TODO: api call

            toggleView(false);
        });


        ArrayAdapter<String> adapterMediaOwner = new ArrayAdapter<String>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.MEDIA_OWNER) {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY: Color.BLACK);
                tv.setTextSize(18);
                return view;
            }
        };
        spinnerMediaOwner.setAdapter(adapterMediaOwner);
        spinnerMediaOwner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                View v = spinnerMediaOwner.getSelectedView();
                TextView tv = ((TextView)v);
                tv.setTextSize(18);
                tv.setTypeface(tv.getTypeface(), Typeface.NORMAL);
                tv.setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spinnerMediaOwner.setSelection(0, false);

        ArrayAdapter<String> adapterFormat = new ArrayAdapter<String>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.FORMAT) {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY: Color.BLACK);
                tv.setTextSize(18);
                return view;
            }
        };
        spinnerFormat.setAdapter(adapterFormat);
        spinnerFormat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                View v = spinnerFormat.getSelectedView();
                TextView tv = ((TextView)v);
                tv.setTextSize(18);
                tv.setTypeface(tv.getTypeface(), Typeface.NORMAL);
                tv.setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spinnerFormat.setSelection(0, false);

        ArrayAdapter<String> adapterEnvironment = new ArrayAdapter<String>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.ENVIRONMENT) {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY: Color.BLACK);
                tv.setTextSize(18);
                return view;
            }
        };
        spinnerEnvironment.setAdapter(adapterEnvironment);
        spinnerEnvironment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                View v = spinnerEnvironment.getSelectedView();
                TextView tv = ((TextView)v);
                tv.setTextSize(18);
                tv.setTypeface(tv.getTypeface(), Typeface.NORMAL);
                tv.setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spinnerEnvironment.setSelection(0, false);
    }

    private void populate() {
        if (isInitialized) {
            if (selectedBillboard == null) {
                toggleView(true);

                textMediaOwner.setText("");
                textFormat.setText("");
                textEnvironment.setText("");
                textLighting.setText("No");
                textNoOfPanels.setText("1");
                textSpeedLimit.setText("<30");

                spinnerMediaOwner.setSelection(0);
                spinnerFormat.setSelection(0);
                spinnerEnvironment.setSelection(0);
                checkboxDigitalScreen.setChecked(false);
                checkboxLighting.setChecked(false);
                ((RadioButton) radioGroupNoPanels.getChildAt(0))
                        .setChecked(true);
                ((RadioButton) radioGroupSpeedLimit.getChildAt(0))
                        .setChecked(true);
            } else {
                toggleView(false);

                textMediaOwner.setText(selectedBillboard.media_owner);
                textFormat.setText(selectedBillboard.format);
                textEnvironment.setText(selectedBillboard.environment);
                textLighting.setText(selectedBillboard.lighting ? "Yes" : "No");
                textNoOfPanels.setText(String.valueOf(selectedBillboard.no_panels));
                textSpeedLimit.setText(selectedBillboard.speed_limit);

                spinnerMediaOwner.setSelection(Config.MEDIA_OWNER.indexOf(selectedBillboard.media_owner));
                spinnerFormat.setSelection(Config.FORMAT.indexOf(selectedBillboard.format));
                spinnerEnvironment.setSelection(Config.ENVIRONMENT.indexOf(selectedBillboard.environment));
                checkboxDigitalScreen.setChecked(selectedBillboard.type.equals("digital"));
                checkboxLighting.setChecked(selectedBillboard.lighting);
                ((RadioButton) radioGroupNoPanels.getChildAt(selectedBillboard.getNoPanelsIndex()))
                        .setChecked(true);
                ((RadioButton) radioGroupSpeedLimit.getChildAt(selectedBillboard.getSpeedLimitIndex()))
                        .setChecked(true);
            }
        }
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
