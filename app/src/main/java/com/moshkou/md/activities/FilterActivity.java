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

    private AutoCompleteTextView auto_complete_state;
    private AutoCompleteTextView auto_complete_city;
    private AutoCompleteTextView auto_complete_media_owner;
    private AutoCompleteTextView auto_complete_format;
    private AutoCompleteTextView auto_complete_advertiser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        auto_complete_state = findViewById(R.id.auto_complete_state);
        auto_complete_city = findViewById(R.id.auto_complete_city);
        auto_complete_media_owner = findViewById(R.id.auto_complete_media_owner);
        auto_complete_format = findViewById(R.id.auto_complete_format);
        auto_complete_advertiser = findViewById(R.id.auto_complete_advertiser);

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
        auto_complete_state.setText(Settings.FILTER_ITEMS.location.state);
        auto_complete_city.setText(Settings.FILTER_ITEMS.location.city);
        auto_complete_media_owner.setText(Settings.FILTER_ITEMS.media_owner);
        auto_complete_format.setText(Settings.FILTER_ITEMS.format);
        auto_complete_advertiser.setText(Settings.FILTER_ITEMS.advertiser);
    }

    private void clear() {
        auto_complete_state.setText("");
        auto_complete_city.setText("");
        auto_complete_media_owner.setText("");
        auto_complete_format.setText("");
        auto_complete_advertiser.setText("");

        Settings.FILTER_ITEMS = new BillboardModel();
    }

    private void done() {
        Settings.FILTER_ITEMS.location.state = auto_complete_state.getText().toString().trim();
        Settings.FILTER_ITEMS.location.city = auto_complete_city.getText().toString().trim();
        Settings.FILTER_ITEMS.media_owner = auto_complete_media_owner.getText().toString().trim();
        Settings.FILTER_ITEMS.format = auto_complete_format.getText().toString().trim();
        Settings.FILTER_ITEMS.advertiser = auto_complete_advertiser.getText().toString().trim();

        backPressed();
    }


}
