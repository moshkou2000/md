package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BillboardModel;

public class FilterActivity extends AppCompatActivity {


    private static String TAG = "FILTER";

    private final Context context = this;

    private AutoCompleteTextView textEditState;
    private AutoCompleteTextView textEditCity;
    private AutoCompleteTextView textEditMediaOwner;
    private AutoCompleteTextView textEditFormat;
    private AutoCompleteTextView textEditAdvertiser;




    /**
     * Override functions ->
     * onCreate
     * onBackPressed
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        textEditState = findViewById(R.id.auto_complete_state);
        textEditCity = findViewById(R.id.auto_complete_city);
        textEditMediaOwner = findViewById(R.id.auto_complete_media_owner);
        textEditFormat = findViewById(R.id.auto_complete_format);
        textEditAdvertiser = findViewById(R.id.auto_complete_advertiser);

        init();
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
        textEditState.setText(Settings.FILTER_BILLBOARD.location.state);
        textEditCity.setText(Settings.FILTER_BILLBOARD.location.city);
        textEditMediaOwner.setText(Settings.FILTER_BILLBOARD.media_owner);
        textEditFormat.setText(Settings.FILTER_BILLBOARD.format);
        textEditAdvertiser.setText(Settings.FILTER_BILLBOARD.advertiser);

        ArrayAdapter<String> adapterState = new ArrayAdapter<>(
                App.getContext(),
                android.R.layout.select_dialog_item,
                Config.STATES);
        textEditState.setThreshold(1);//will start working from first character
        textEditState.setAdapter(adapterState);
        textEditState.setOnItemClickListener((parent, v, position, id) -> {
            ArrayAdapter<String> adapterCity = new ArrayAdapter<>(
                    App.getContext(),
                    android.R.layout.select_dialog_item,
                    Utils.getCities(adapterState.getItem(position)));
            textEditCity.setThreshold(1);//will start working from first character
            textEditCity.setAdapter(adapterCity);
        });

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
     * clear
     * done
     * setSelectedStyle
     * backPressed
     */

    private void clear() {
        textEditState.setText("");
        textEditCity.setText("");
        textEditMediaOwner.setText("");
        textEditFormat.setText("");
        textEditAdvertiser.setText("");

        Settings.FILTER_BILLBOARD = new BillboardModel();

        backPressed();
    }

    private void done() {
        Settings.FILTER_BILLBOARD.location.state = textEditState.getText().toString().trim();
        Settings.FILTER_BILLBOARD.location.city = textEditCity.getText().toString().trim();
        Settings.FILTER_BILLBOARD.media_owner = textEditMediaOwner.getText().toString().trim();
        Settings.FILTER_BILLBOARD.format = textEditFormat.getText().toString().trim();
        Settings.FILTER_BILLBOARD.advertiser = textEditAdvertiser.getText().toString().trim();

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
