package com.moshkou.md.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.moshkou.md.Configs.Flags;
import com.moshkou.md.Configs.Keys;
import com.moshkou.md.Controls.SharedPreferencesControl;
import com.moshkou.md.Helpers.Utils;
import com.moshkou.md.Models.UserModel;
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
        Utils.setPictureDirectory(this);

        String data = SharedPreferencesControl.get(this, Keys.USER);
        Gson gson = new Gson();
        Utils.gUser = gson.fromJson(data, UserModel.class);

        if (Utils.gUser != null) {
            String token = SharedPreferencesControl.get(this, Keys.TOKEN);

            if (!token.isEmpty())
                Utils.gUser.setToken(token);
        }

        Intent i = new Intent(this, CarouselTipsActivity.class);
        i.putExtra(Keys.FLAG, Flags.LOGIN);
        startActivity(i);
        finish();
    }
}