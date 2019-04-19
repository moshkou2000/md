package com.moshkou.md.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;

/**
 * A login screen that offers login via email/password.
 */
public class BookingActivity extends AppCompatActivity {


    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Button btnFrom = findViewById(R.id.from);
        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BookingListActivity.class);
                startActivity(i);
            }
        });
    }

}
