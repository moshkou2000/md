package com.moshkou.md.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.R;

public class AlertActivity extends Activity {


    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Button signInButton = ((Button) findViewById(R.id.sign_in_button));
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, LoginActivity.class);
                i.putExtra(Keys.FLAG, Flags.LOGOUT);
                startActivity(i);
                finish();
            }
        });
    }
}
