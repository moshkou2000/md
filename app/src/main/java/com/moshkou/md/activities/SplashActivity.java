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

    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        launch();
    }

    private void launch() {

        Utils.getDeviceSize(context);
        Utils.getAppPictureDirectory();

        String data = SharedPreferencesSupport.getString(context, Keys.USER);
        Gson gson = new Gson();
        Settings.user = gson.fromJson(data, UserModel.class);

        if (Settings.user != null) {
            String token = SharedPreferencesSupport.getString(context, Keys.TOKEN);

            if (!token.isEmpty())
                Settings.user.setToken(token);
        }

        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(Keys.FLAG, Flags.LOGIN);
        startActivity(i);
        finish();
    }
}