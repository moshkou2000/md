package com.moshkou.md.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardModel;


public class BillboardFragment extends Fragment {


    private static String TAG = "LOCATION_FRG";

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
    private boolean isEdit = false;



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
        buttonEdit.setOnClickListener(v -> toggleView());
        buttonCancel.setOnClickListener(v -> toggleView());
        buttonSave.setOnClickListener(v -> {
            // TODO: api call

            toggleView();
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
            textMediaOwner.setText(selectedBillboard.location.name);
            textFormat.setText(selectedBillboard.format);
            textSize.setText(selectedBillboard.location.address);
            textEnvironment.setText(selectedBillboard.location.city);
            textLighting.setText(selectedBillboard.location.state);
            textNoOfPanels.setText(String.valueOf(selectedBillboard.location.postcode));
            textSpeedLimit.setText(selectedBillboard.location.country);

            textEditMediaOwner.setText(selectedBillboard.media_owner);
            textEditFormat.setText(selectedBillboard.format);
            textEditSize.setText(selectedBillboard.size);
            textEditEnvironment.setText(selectedBillboard.environment);
//            checkboxDigitalScreen.setText(selectedBillboard.type);
//            checkboxLighting.setText(selectedBillboard.lighting);
//            radioGroupNoPanels.setText(selectedBillboard.no_panels);
//            radioGroupSpeedLimit.setText(selectedBillboard.speed_limit);
        }
    }
}
