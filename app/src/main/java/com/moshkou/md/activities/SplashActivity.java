package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.helpers.SharedPreferencesSupport;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.UserModel;
import com.moshkou.md.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        launch();
    }

    private void launch() {

        final Context context = this;

        Utils.init(context);

        String data = SharedPreferencesSupport.getString(context, Keys.USER);
        Settings.USER = new Gson().fromJson(data, UserModel.class);

        if (Settings.USER != null)
            startActivity(new Intent(context, MainActivity.class));
        else
            startActivity(new Intent(context, LoginActivity.class));

        finish();
    }
}