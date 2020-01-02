package com.moshkou.md.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.R;

public class LoginVerificationActivity extends AppCompatActivity {


    private EditText code;
    private TextView request;
    private TextView error;
    private Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verification);

        code = findViewById(R.id.code);
        request = findViewById(R.id.request);
        error = findViewById(R.id.error);
        verify = findViewById(R.id.verify);

        request.setOnClickListener(view -> countDown());
        verify.setOnClickListener(view -> verification());

        countDown();
    }

    private void verification() {
        if (validation()) {

            // TODO: validation*****************

            gotoSomething();
        }
    }

    private boolean validation() {
        boolean isEmpty = code.getText().length() < 4;

        if(isEmpty) {
            error.setText(R.string.message_error_required_verification_code);
        }

        return !isEmpty;
    }

    private void gotoSomething() {
        Intent i = new Intent(this, LoginSuspiciousActivity.class);
        i.putExtra(Keys.FLAG, Flags.LOGIN);
        startActivity(i);
        finish();
    }

    private void countDown() {
        code.getText().clear();
        error.setText("");

        new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                request.setText(getString(R.string.hint_login_verification_request,
                        Utils.humanizerCountDown(millisUntilFinished / 1000)));
            }

            public void onFinish() {
                request.setText(Html.fromHtml(getString(R.string.hint_login_verification_resend)));
            }
        }.start();
    }

}