package com.moshkou.md.activities;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.R;

public class ProfileActivity extends AppCompatActivity {


    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Test Title");
        toolbar.setSubtitle("Test Subtitle");
        toolbar.inflateMenu(R.menu.profile);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action1:
                        Utils.toast(context, Enumerates.Message.ERROR, "1", Toast.LENGTH_LONG);
                        return true;
                    case R.id.action2:
                        Utils.toast(context, Enumerates.Message.ERROR, "22", Toast.LENGTH_LONG);
                        return true;
                    case R.id.actionMore:
                        Utils.toast(context, Enumerates.Message.ERROR, "333", Toast.LENGTH_LONG);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
