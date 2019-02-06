package com.moshkou.md.Activities;

import android.Manifest;
import android.animation.Animator;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moshkou.md.Configs.Enumerates;
import com.moshkou.md.Configs.RequestCode;
import com.moshkou.md.Controls.DateTimePickerControl;
import com.moshkou.md.Helpers.Utils;
import com.moshkou.md.Models.DatetimeModel;
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
    private Button datetime;
    private Button profile;
    private Button level;
    private LinearLayout datetimePickerContainer;
    private DateTimePickerControl datetimePicker;


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
        datetime = findViewById(R.id.datetime);
        profile = findViewById(R.id.profile);
        level = findViewById(R.id.level);

        datetimePickerContainer = findViewById(R.id.datetimePickerContainer);
        datetimePickerContainer.setTranslationY(1000);

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
                //startActivity(new Intent(context, ContactsActivity.class));
                datetimePicker.setDatetime(2017, 0, 1, 1, 1);
            }
        });
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datetimePicker == null) {
                    datetimePicker = new DateTimePickerControl(context);
                    datetimePicker.setOnTimeChangedListener(new DateTimePickerControl.OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(DateTimePickerControl view, Enumerates.ConfirmationState confirmationState, DatetimeModel datetimeModel) {
                            if (confirmationState == Enumerates.ConfirmationState.NULL) {
                                // value changed
                            } else {
                                if (confirmationState == Enumerates.ConfirmationState.OK) {
                                    // DONE
                                } else if (confirmationState == Enumerates.ConfirmationState.CANCEL) {
                                    // CANCEL
                                }

                                datetimePickerContainer
                                        .animate()
                                        .translationY(datetimePicker.getHeight())
                                        .alpha(1.0f)
                                        .setListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {
                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                datetimePicker = null;
                                                datetimePickerContainer.removeAllViews();
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {
                                                datetimePicker = null;
                                                datetimePickerContainer.removeAllViews();
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {
                                            }
                                        });
                            }
                        }
                    });

                    datetimePickerContainer.addView(datetimePicker);
                    datetimePickerContainer.animate().translationY(0).alpha(1.0f).setListener(null);
                }
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ProfileActivity.class));
            }
        });
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LevelsActivity.class));
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
