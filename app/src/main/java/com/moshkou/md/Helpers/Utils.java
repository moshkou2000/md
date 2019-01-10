package com.moshkou.md.Helpers;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.moshkou.md.Activities.AlertActivity;
import com.moshkou.md.Activities.PreviewActivity;
import com.moshkou.md.Configs.Config;
import com.moshkou.md.Configs.Flags;
import com.moshkou.md.Configs.Keys;
import com.moshkou.md.Controls.SharedPreferencesControl;
import com.moshkou.md.Models.UserModel;
import com.moshkou.md.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class Utils {

    public static UserModel gUser;

    public static String gPictureDirectory = "";

    public static boolean gHasACCESS_NETWORK_STATE = false;
    public static boolean gHasREAD_EXTERNAL_STORAGE = false;
    public static boolean gHasWRITE_EXTERNAL_STORAGE = false;


    public static String getCdnUrl(String url) {
        return Config.CDN_URL + url + "?token=" + Utils.gUser.getToken();
    }
    public static String getPreviewUrl(String url) {
        return Config.PREVIEW_URL + url + "?token=" + Utils.gUser.getToken();
    }

    public static String humanizedFileSize(int bytes) {
        return String.format("%.2f MB", bytes * 1.0 / 1048576); // 1MB = 1048576 bytes
    }
    public static String humanizedDuration(String seconds) {
        String duration = "";
        try {
            duration = String.format("%.2f", Double.valueOf(seconds) * 1.0 / 60);
        } catch (Exception ex) {}
        return duration;
    }
    public static String humanizedResolution(String width, String height) {
        return width == null || height == null ? "" : width + " X " + height;
    }
    public static String humanizedResolution(int width, int height) {
        return width < 1 || height < 1 ? "" : width + " X " + height;
    }
    public static String humanizerCountDown(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        //long h = (seconds / (60 * 60)) % 24;
        return String.format("%2d:%02d",m,s);
    }

    public static void requestPermission(Activity activity) {
        requestPermission_ACCESS_NETWORK_STATE(activity);
        requestPermission_READ_EXTERNAL_STORAGE(activity);
    }
    public static void requestPermission_ACCESS_NETWORK_STATE(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[] { Manifest.permission.ACCESS_NETWORK_STATE },
                    Flags.ACCESS_NETWORK_STATE_REQUEST_CODE);
        } else {
            Utils.gHasACCESS_NETWORK_STATE = true;
        }
    }
    public static void requestPermission_READ_EXTERNAL_STORAGE(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    Flags.READ_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            Utils.gHasREAD_EXTERNAL_STORAGE = true;
            Utils.gHasWRITE_EXTERNAL_STORAGE = true;
        }
    }

    public static void tokenExpired(Context context) {
        logout(context);
        context.startActivity(new Intent(context, AlertActivity.class));
    }
    public static void logout(Context context) {
        gUser = null;
        SharedPreferencesControl.set(context, Keys.USER, "");
    }
    public static void switchLicense() {
        Utils.gUser.setToken("");
    }

    public static boolean setPictureDirectory(Context context) {
        File folder = context.getExternalFilesDir(Config.APP_NAME);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        gPictureDirectory = folder.getAbsolutePath();
        return success;
    }

    public static void getDeviceSize(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Config.DEVICE_WIDTH     = (double) displayMetrics.widthPixels;
            Config.DEVICE_HEIGHT    = (double) displayMetrics.heightPixels;
            Config.DEVICE_DENSITY   = displayMetrics.density;

            // gridView(verticalSpacing & padding) is 1
            Config.SIZE_2_COLUMNS   = (int) ((Config.DEVICE_WIDTH - displayMetrics.density * 1) / 2);
            Config.SIZE_3_COLUMNS   = (int) ((Config.DEVICE_WIDTH - displayMetrics.density * 2) / 3);
            Config.SIZE_35_COLUMNS = (int) ((Config.DEVICE_WIDTH - displayMetrics.density * 2) / 3.5);

        } catch (Exception ex) {
            Log.d("ERROR", "Unable to get device metrics");
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void toast(Context context, Keys.MessageState messageState, String message) {
        View layout = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        LinearLayout root = (LinearLayout) layout.findViewById(R.id.toast_layout_root);

        if(messageState == Keys.MessageState.INFO) {
            root.setBackgroundResource(R.drawable.ic_toast_info);
        } else if(messageState == Keys.MessageState.WARNING) {
            root.setBackgroundResource(R.drawable.ic_toast_warning);
        } else if(messageState == Keys.MessageState.ERROR) {
            root.setBackgroundResource(R.drawable.ic_toast_error);
        }

        TextView text = (TextView) layout.findViewById(R.id.textView_message);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, 60);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void activityPreview(Context context, String url, String name, boolean isVideo){
        Intent i = new Intent(context, PreviewActivity.class);
        i.putExtra(Keys.URL, url);
        i.putExtra(Keys.NAME, name);
        i.putExtra(Keys.VIDEO, isVideo);
        context.startActivity(i);
    }

}
