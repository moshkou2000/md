package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.moshkou.md.configs.Config;
import com.moshkou.md.R;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.helpers.SharedPreferencesSupport;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnLoginListener;
import com.moshkou.md.models.UserModel;
import com.moshkou.md.services.Auth;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements
        OnLoginListener {

    private static final String TAG = "LOGIN";


    private final Context context = this;
    private EditText textEditPhone;
    private EditText textEditPassword;
    private TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textEditPhone = findViewById(R.id.edit_text_phone);
        textEditPassword = findViewById(R.id.edit_text_password);
        error = findViewById(R.id.error);
        Button signIn = findViewById(R.id.signin);
        signIn.setOnClickListener(view -> login());
    }

    private void login() {
        error.setText("");
        String strPhone = textEditPhone.getText().toString().trim();
        String strPassword = textEditPassword.getText().toString().trim();

        Log.i(TAG, "p: " + strPassword);

        if (strPhone.length() == 0) {
            error.setText(R.string.message_error_phone);

        } else if (!strPhone.matches(Config.REGEX_NUMBER)) {
            error.setText(R.string.message_error_valid_phone);

        } else if (strPassword.length() == 0) {
            error.setText(R.string.message_error_password);

        } else if (strPassword.length() < 6 || strPassword.length() > 16) {
            error.setText(R.string.message_error_valid_password);

        } else {
            JSONObject data = new JSONObject();
            try {
                data.put("phone", strPhone);
                data.put("password", Utils.toMd5(strPhone + strPassword));
                Auth.login(this, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




    @Override
    public void onLoginInteraction(UserModel user) {
        if (user != null) {
            Settings.USER = user;
            SharedPreferencesSupport.setString(context, Keys.USER, new Gson().toJson(user));

            startActivity(new Intent(context, MainActivity.class));
            finish();
        }
    }
}

