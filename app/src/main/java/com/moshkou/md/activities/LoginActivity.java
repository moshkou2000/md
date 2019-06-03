package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moshkou.md.configs.Config;
import com.moshkou.md.R;

public class LoginActivity extends AppCompatActivity {


    private final Context context = this;
    private TextView signup;
    private EditText phoneEmail;
    private TextView error;
    private Button signin;
    private Button help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneEmail = findViewById(R.id.phone_email);
        error = findViewById(R.id.error);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        help = findViewById(R.id.help);

        signin.setOnClickListener(view -> {
            if (validation())
                gotoLoginVerification();
        });
        signup.setOnClickListener(view -> gotoRegister());
        help.setOnClickListener(view -> gotoHelp());
    }

    private boolean validation() {
        error.setText("");

        String strPhoneEmail = phoneEmail.getText().toString();
        boolean bPhoneEmail= strPhoneEmail.matches(Config.REGEX_NUMBER);

        if (strPhoneEmail.isEmpty()) {
            error.setText(R.string.message_error_valid_phone_email);
        } else if (bPhoneEmail) {
            bPhoneEmail = strPhoneEmail.length() > 9;
            if (!bPhoneEmail) {
                error.setText(R.string.message_error_valid_phone);
            }
        } else {
            bPhoneEmail = Patterns.EMAIL_ADDRESS.matcher(strPhoneEmail).matches();
            if (!bPhoneEmail) {
                error.setText(R.string.message_error_valid_email);
            }
        }

        return !strPhoneEmail.isEmpty() && bPhoneEmail;
    }

    private void gotoLoginVerification() {
        reset();

        Intent i = new Intent(context, LoginVerificationActivity.class);
        startActivity(i);
        finish();
    }

    private void gotoRegister() {
        Intent i = new Intent(context, RegisterActivity.class);
        startActivity(i);
        finish();
    }

    private void gotoHelp() {
        Intent i = new Intent(context, HelpActivity.class);
        startActivity(i);
    }

    private void reset() {
        phoneEmail.getText().clear();
        error.setText("");
    }
}

