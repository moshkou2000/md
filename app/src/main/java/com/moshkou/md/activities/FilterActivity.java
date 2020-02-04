package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.controls.AutoCompleteTextViewControl;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BillboardModel;


public class FilterActivity extends AppCompatActivity {


    private static String TAG = "FILTER";

    private final Context context = this;

    private AutoCompleteTextViewControl autoCompleteState;
    private AutoCompleteTextViewControl autoCompleteCity;
    private AutoCompleteTextViewControl autoCompleteFormat;
    private AutoCompleteTextViewControl autoCompleteMediaOwner;
    private AutoCompleteTextViewControl autoCompleteAdvertiser;




    /**
     * Override functions ->
     * onCreate
     * onBackPressed
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        autoCompleteState = findViewById(R.id.auto_complete_state);
        autoCompleteCity = findViewById(R.id.auto_complete_city);
        autoCompleteFormat = findViewById(R.id.auto_complete_format);
        autoCompleteMediaOwner = findViewById(R.id.auto_complete_media_owner);
        autoCompleteAdvertiser = findViewById(R.id.auto_complete_advertiser);

        init();
        populate();
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }




    /**
     * Init functions ->
     * init
     * initToolbar
     */

    private void init() {
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

        // autoComplete format
        ArrayAdapter<String> adapterFormat = new ArrayAdapter<String>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.FORMAT);
        autoCompleteFormat.setAdapter(adapterFormat);

        // autoComplete mediaOwner
        ArrayAdapter<String> adapterMediaOwner = new ArrayAdapter<>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.MEDIA_OWNER);
        autoCompleteMediaOwner.setAdapter(adapterMediaOwner);

        // autoComplete advertiser
        ArrayAdapter<String> adapterAdvertiser = new ArrayAdapter<>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.ADVERTISER);
        autoCompleteAdvertiser.setAdapter(adapterAdvertiser);

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_filter);
        toolbar.inflateMenu(R.menu.filter);
        toolbar.setNavigationOnClickListener(view -> backPressed());
        toolbar.setOnMenuItemClickListener(menuItem -> setSelectedStyle(menuItem.getItemId()));
    }




    /**
     * Helper functions ->
     * populate
     * clear
     * done
     * setSelectedStyle
     * backPressed
     */

    private void populate() {
        autoCompleteState.setText(Settings.FILTER_BILLBOARD.location.state);
        autoCompleteCity.setText(Settings.FILTER_BILLBOARD.location.city);
        autoCompleteFormat.setText(Settings.FILTER_BILLBOARD.format);
        autoCompleteMediaOwner.setText(Settings.FILTER_BILLBOARD.media_owner);
        autoCompleteAdvertiser.setText(Settings.FILTER_BILLBOARD.advertiser);

        Log.i(TAG, "x: " + Settings.FILTER_BILLBOARD.format);
        Log.i(TAG, "y: " + Config.FORMAT.indexOf(Settings.FILTER_BILLBOARD.format));
    }

    private void clear() {
        autoCompleteState.setSelection(0);
        autoCompleteCity.setSelection(0);
        autoCompleteFormat.setSelection(0);
        autoCompleteMediaOwner.setText("");
        autoCompleteAdvertiser.setText("");

        Settings.FILTER_BILLBOARD = new BillboardModel();

        backPressed();
    }

    private void done() {
        Settings.FILTER_BILLBOARD.location.state = autoCompleteState.getText().toString();
        Settings.FILTER_BILLBOARD.location.city = autoCompleteCity.getText().toString();
        Settings.FILTER_BILLBOARD.format = autoCompleteFormat.getText().toString();
        Settings.FILTER_BILLBOARD.media_owner = autoCompleteMediaOwner.getText().toString().trim();
        Settings.FILTER_BILLBOARD.advertiser = autoCompleteAdvertiser.getText().toString().trim();

        backPressed();
    }

    private boolean setSelectedStyle(int selectedStyleId) {
        switch (selectedStyleId) {
            case R.id.action1:
                clear();
                break;
            case R.id.action2:
                done();
                break;
            default:
                break;
        }

        return true;
    }

    private void backPressed() {
        Utils.hideKeyboard(this);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra(Keys.TYPE, Keys.FILTER);
            startActivity(i);
            finish();
        }, 200);
    }
}
