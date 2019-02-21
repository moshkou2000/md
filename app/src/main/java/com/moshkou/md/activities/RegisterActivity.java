package com.moshkou.md.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moshkou.md.R;

public class RegisterActivity extends Activity {


    private final Context context = this;
    private TextView signin;
    private EditText name;
    private EditText phone;
    private EditText email;
    private TextView error;
    private Button signup;
    private Button help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        error = findViewById(R.id.error);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        help = findViewById(R.id.help);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    gotoLoginVerification();
                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHelp();
            }
        });
    }

    private boolean validation() {
        error.setText("");

        boolean bName = name.getText().length() > 2;
        boolean bPhone = phone.getText().length() > 9;
        boolean bEmail = Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches();

        if (!(bName && bPhone)) {
            error.setText(R.string.message_error_required_fields);
        } else if (!bEmail) {
            error.setText(R.string.message_error_valid_email);
        }

        return bName && bPhone && bEmail;
    }

    private void gotoLoginVerification() {
        reset();

        Intent i = new Intent(context, LoginVerificationActivity.class);
        startActivity(i);
        finish();
    }

    private void gotoLogin() {
        reset();

        Intent i = new Intent(context, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void gotoHelp() {
        Intent i = new Intent(context, HelpActivity.class);
        startActivity(i);
    }

    private void reset() {
        name.getText().clear();
        phone.getText().clear();
        email.getText().clear();
        error.setText("");
    }

}