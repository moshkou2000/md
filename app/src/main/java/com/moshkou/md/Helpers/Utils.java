package com.moshkou.md.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moshkou.md.Activities.PreviewActivity;
import com.moshkou.md.Configs.Data;
import com.moshkou.md.Configs.Keys;
import com.moshkou.md.Configs.Enumerates;
import com.moshkou.md.R;

import java.io.File;

public class Utils {

    public static String humanizedFileSize(int bytes) {
        return String.format("%.2f MB", bytes * 1.0 / 1048576); // 1MB = 1048576 bytes
    }
    public static String humanizerCountDown(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        //long h = (seconds / (60 * 60)) % 24;
        return String.format("%2d:%02d", m, s);
    }

    public static void getAppPictureDirectory(Context context) {
        File folder = context.getExternalFilesDir(context.getString(R.string.app_name));
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Data.APP_PICTURE_DIRECTORY = folder.getAbsolutePath();
    }
    public static void getDeviceSize(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Data.DEVICE_WIDTH     = (double) displayMetrics.widthPixels;
            Data.DEVICE_HEIGHT    = (double) displayMetrics.heightPixels;
            Data.DEVICE_DENSITY   = displayMetrics.density;
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

    public static void toast(Context context, Enumerates.Message messageState, String message) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toast, null);
        LinearLayout root = (LinearLayout) layout.findViewById(R.id.toast_layout_root);

        if(messageState == Enumerates.Message.INFO) {
            root.setBackgroundResource(R.drawable.ic_toast_info);
        } else if(messageState == Enumerates.Message.WARNING) {
            root.setBackgroundResource(R.drawable.ic_toast_warning);
        } else if(messageState == Enumerates.Message.ERROR) {
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
