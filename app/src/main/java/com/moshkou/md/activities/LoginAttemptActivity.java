package com.moshkou.md.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.moshkou.md.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginAttemptActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_attempt);
    }
}

