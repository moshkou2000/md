package com.moshkou.md.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moshkou.md.App;
import com.moshkou.md.activities.PreviewActivity;
import com.moshkou.md.configs.Data;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.R;
import com.moshkou.md.configs.Permission;

import java.io.File;

public class Utils {

    @SuppressLint("DefaultLocale")
    public static String humanizerFileSize(int bytes) {
        return String.format("%.2f MB", bytes * 1.0 / 1048576); // 1MB = 1048576 bytes
    }
    @SuppressLint("DefaultLocale")
    public static String humanizerCountDown(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        //long h = (seconds / (60 * 60)) % 24;
        return String.format("%2d:%02d", m, s);
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static Boolean makePrivateInternalStoragePictureDirectory() {
        if (isExternalStorageWritable()) {
            // Get the directory for the app's private pictures directory.
            File file = new File(App.getContext().getFilesDir(), Environment.DIRECTORY_PICTURES);
            if (!file.mkdirs()) {
                Log.i("UTILSss", "Private Directory not created");
            }
            Data.APP_PICTURE_DIRECTORY = file.getPath();
            return file.mkdirs();
        }

        return false;
    }

    public static Boolean makePrivateExternalStoragePictureDirectory() {
        if (isExternalStorageWritable()) {
            // Get the directory for the app's private pictures directory.
            File file = new File(App.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), App.getContext().getString(R.string.app_name));
            if (!file.mkdirs()) {
                Log.i("UTILS", "Private Directory not created");
            }
            Data.APP_PICTURE_DIRECTORY = file.getPath();
            return file.mkdirs();
        }

        return false;
    }

    public static Boolean makePublicExternalStoragePictureDirectory() {
        if (isExternalStorageWritable()) {
            // Get the directory for the user's public pictures directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), App.getContext().getString(R.string.app_name));
            if (!file.mkdirs()) {
                Log.i("UTILS", "Public Directory not created");
            }
            Data.APP_PICTURE_DIRECTORY = file.getPath();
            return file.mkdirs();
        }

        return false;
    }

    public static void getAppPictureDirectory() {
        makePrivateExternalStoragePictureDirectory();
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
        LinearLayout root = layout.findViewById(R.id.toast_layout_root);

        if(messageState == Enumerates.Message.INFO) {
            root.setBackgroundResource(R.drawable.ic_toast_info);
        } else if(messageState == Enumerates.Message.WARNING) {
            root.setBackgroundResource(R.drawable.ic_toast_warning);
        } else if(messageState == Enumerates.Message.ERROR) {
            root.setBackgroundResource(R.drawable.ic_toast_error);
        }

        TextView text = layout.findViewById(R.id.textView_message);
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