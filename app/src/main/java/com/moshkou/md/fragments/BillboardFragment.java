package com.moshkou.md.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.helpers.Utils;
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
    private TextView textSize;
    private TextView textEnvironment;
    private TextView textLighting;
    private TextView textNoOfPanels;
    private TextView textSpeedLimit;

    private AutoCompleteTextView textEditMediaOwner;
    private AutoCompleteTextView textEditFormat;
    private AutoCompleteTextView textEditSize;
    private AutoCompleteTextView textEditEnvironment;
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
        textSize = view.findViewById(R.id.text_size);
        textEnvironment = view.findViewById(R.id.text_environment);
        textLighting = view.findViewById(R.id.text_lighting);
        textNoOfPanels = view.findViewById(R.id.text_no_of_panels);
        textSpeedLimit = view.findViewById(R.id.text_speed_limit);

        textEditMediaOwner = view.findViewById(R.id.text_edit_media_owner);
        textEditFormat = view.findViewById(R.id.text_edit_format);
        textEditSize = view.findViewById(R.id.text_edit_size);
        textEditEnvironment = view.findViewById(R.id.text_edit_environment);
        checkboxDigitalScreen = view.findViewById(R.id.checkbox_digital_screen);
        checkboxLighting = view.findViewById(R.id.checkbox_lighting);
        radioGroupNoPanels = view.findViewById(R.id.radio_group_no_panels);
        radioGroupSpeedLimit = view.findViewById(R.id.radio_group_speed_limit);

        buttonEdit = view.findViewById(R.id.button_edit);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonSave = view.findViewById(R.id.button_save);

        Log.i(TAG, "init");

        init();
//        populate();

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


        ArrayAdapter<String> adapterMediaOwner = new ArrayAdapter<>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.STATES);
        textEditMediaOwner.setThreshold(1);//will start working from first character
        textEditMediaOwner.setAdapter(adapterMediaOwner);
        textEditMediaOwner.setOnItemClickListener((parent, v, position, id) -> { });

        ArrayAdapter<String> adapterFormat = new ArrayAdapter<>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.STATES);
        textEditFormat.setThreshold(1);//will start working from first character
        textEditFormat.setAdapter(adapterFormat);
        textEditFormat.setOnItemClickListener((parent, v, position, id) -> { });

        ArrayAdapter<String> adapterSize = new ArrayAdapter<>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.STATES);
        textEditSize.setThreshold(1);//will start working from first character
        textEditSize.setAdapter(adapterSize);
        textEditSize.setOnItemClickListener((parent, v, position, id) -> { });

        ArrayAdapter<String> adapterEnvironment = new ArrayAdapter<>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.STATES);
        textEditEnvironment.setThreshold(1);//will start working from first character
        textEditEnvironment.setAdapter(adapterEnvironment);
        textEditEnvironment.setOnItemClickListener((parent, v, position, id) -> { });
    }

    private void populate() {
        if (isInitialized) {
            if (selectedBillboard == null) {
                toggleView(true);

                textMediaOwner.setText("");
                textFormat.setText("");
                textSize.setText("");
                textEnvironment.setText("");
                textLighting.setText("No");
                textNoOfPanels.setText("1");
                textSpeedLimit.setText("<30");

                textEditMediaOwner.setText("");
                textEditFormat.setText("");
                textEditSize.setText("");
                textEditEnvironment.setText("");
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
                textSize.setText(selectedBillboard.size);
                textEnvironment.setText(selectedBillboard.environment);
                textLighting.setText(selectedBillboard.lighting ? "Yes" : "No");
                textNoOfPanels.setText(String.valueOf(selectedBillboard.no_panels));
                textSpeedLimit.setText(selectedBillboard.speed_limit);

                textEditMediaOwner.setText(selectedBillboard.media_owner);
                textEditFormat.setText(selectedBillboard.format);
                textEditSize.setText(selectedBillboard.size);
                textEditEnvironment.setText(selectedBillboard.environment);
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
