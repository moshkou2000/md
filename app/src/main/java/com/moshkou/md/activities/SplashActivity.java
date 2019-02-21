package com.moshkou.md.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.moshkou.md.configs.Data;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.helpers.SharedPreferencesSupport;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.UserModel;
import com.moshkou.md.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        launch();
    }

    private void launch() {

        Utils.getDeviceSize(this);
        Utils.getAppPictureDirectory();

        String data = SharedPreferencesSupport.getString(this, Keys.USER);
        Gson gson = new Gson();
        Data.user = gson.fromJson(data, UserModel.class);

        if (Data.user != null) {
            String token = SharedPreferencesSupport.getString(this, Keys.TOKEN);

            if (!token.isEmpty())
                Data.user.setToken(token);
        }

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(Keys.FLAG, Flags.LOGIN);
        startActivity(i);
        finish();
    }
}