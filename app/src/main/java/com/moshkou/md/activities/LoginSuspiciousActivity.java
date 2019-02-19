package com.moshkou.md.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginSuspiciousActivity extends AppCompatActivity {



    private Button send_security_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_suspicious);

        send_security_code = findViewById(R.id.send_security_code);

        send_security_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoginVerification();
            }
        });
    }

    private void gotoLoginVerification() {
        Intent i = new Intent(this, LoginVerificationActivity.class);
        i.putExtra(Keys.FLAG, Flags.LOGIN);
        startActivity(i);
        finish();
    }
}

