package com.moshkou.md.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.moshkou.md.Configs.Enumerates;
import com.moshkou.md.Configs.Flags;
import com.moshkou.md.Configs.Keys;
import com.moshkou.md.Configs.Permission;
import com.moshkou.md.Configs.RequestCode;
import com.moshkou.md.Helpers.Utils;
import com.moshkou.md.R;

public class MainActivity extends AppCompatActivity {


    private Context context = this;

    private Button snackbar;
    private Button toast;
    private Button alert;
    private Button attempt;
    private Button carousel;
    private Button login;
    private Button dialogh;
    private Button contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snackbar = findViewById(R.id.snackbar);
        toast = findViewById(R.id.toast);
        alert = findViewById(R.id.alert);
        attempt = findViewById(R.id.attempt);
        carousel = findViewById(R.id.carousel);
        login = findViewById(R.id.login);
        dialogh = findViewById(R.id.dialogh);
        contacts = findViewById(R.id.contacts);

        snackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View coordinatorLayout = findViewById(R.id.coordinatorLayout);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });
                snackbar.show();
            }
        });
        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast(context, Enumerates.Message.ERROR, "Errorr dari na");
            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AlertActivity.class));
            }
        });
        attempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginAttemptActivity.class));
            }
        });
        carousel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CarouselTipsActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
            }
        });
        dialogh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog);

                TextView header_text = dialog.findViewById(R.id.header_text);
                TextView text_body = dialog.findViewById(R.id.text_body);
                Button cancel = dialog.findViewById(R.id.btn_cancel);
                Button ok = dialog.findViewById(R.id.btn_ok);

                header_text.setText("This is sample header");
                text_body.setText("This is sample body text.");


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ContactsActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case RequestCode.ACCESS_NETWORK_STATE:
                if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_NETWORK_STATE)) {
                    // TODO: display "Permission was denied. Display an error message."
                }
                break;

        }
    }

}
