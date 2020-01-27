package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moshkou.md.R;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BillboardModel;

public class FilterActivity extends AppCompatActivity {


    private static String TAG = "FILTER";

    private final Context context = this;

    private AutoCompleteTextView autoCompleteState;
    private AutoCompleteTextView autoCompleteCity;
    private AutoCompleteTextView autoCompleteMediaOwner;
    private AutoCompleteTextView autoCompleteFormat;
    private AutoCompleteTextView autoCompleteAdvertiser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        autoCompleteState = findViewById(R.id.auto_complete_state);
        autoCompleteCity = findViewById(R.id.auto_complete_city);
        autoCompleteMediaOwner = findViewById(R.id.auto_complete_media_owner);
        autoCompleteFormat = findViewById(R.id.auto_complete_format);
        autoCompleteAdvertiser = findViewById(R.id.auto_complete_advertiser);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_filter);
        toolbar.inflateMenu(R.menu.filter);
        toolbar.setNavigationOnClickListener(view -> backPressed());
        toolbar.setOnMenuItemClickListener(menuItem -> setSelectedStyle(menuItem.getItemId()));

        init();
    }

    @Override
    public void onBackPressed() {
        backPressed();
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

    private void init() {
        autoCompleteState.setText(Settings.FILTER_BILLBOARD.location.state);
        autoCompleteCity.setText(Settings.FILTER_BILLBOARD.location.city);
        autoCompleteMediaOwner.setText(Settings.FILTER_BILLBOARD.media_owner);
        autoCompleteFormat.setText(Settings.FILTER_BILLBOARD.format);
        autoCompleteAdvertiser.setText(Settings.FILTER_BILLBOARD.advertiser);
    }

    private void clear() {
        autoCompleteState.setText("");
        autoCompleteCity.setText("");
        autoCompleteMediaOwner.setText("");
        autoCompleteFormat.setText("");
        autoCompleteAdvertiser.setText("");

        Settings.FILTER_BILLBOARD = new BillboardModel();

        backPressed();
    }

    private void done() {
        Settings.FILTER_BILLBOARD.location.state = autoCompleteState.getText().toString().trim();
        Settings.FILTER_BILLBOARD.location.city = autoCompleteCity.getText().toString().trim();
        Settings.FILTER_BILLBOARD.media_owner = autoCompleteMediaOwner.getText().toString().trim();
        Settings.FILTER_BILLBOARD.format = autoCompleteFormat.getText().toString().trim();
        Settings.FILTER_BILLBOARD.advertiser = autoCompleteAdvertiser.getText().toString().trim();

        backPressed();
    }


}
