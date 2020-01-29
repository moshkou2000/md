package com.moshkou.md.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.moshkou.md.App;
import com.moshkou.md.activities.PreviewActivity;
import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.R;
import com.moshkou.md.models.KeyValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Utils {
//    2020-01-14T02:28:45.480Z

    @SuppressLint("DefaultLocale")
    public static String humanizerDateTime(String dateTime) {

        long now = System.currentTimeMillis();
        long lastUpdated = now;

        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat(Config.PATTER_DATETIME);

            Date date = sdf.parse(dateTime);
            lastUpdated = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (String) DateUtils.getRelativeTimeSpanString(lastUpdated, now, DateUtils.DAY_IN_MILLIS);
    }

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
            Settings.APP_PICTURE_DIRECTORY = file.getPath();
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
            Settings.APP_PICTURE_DIRECTORY = file.getPath();
            return file.mkdirs();
        }

        return false;
    }

    public static Boolean makePublicExternalStoragePictureDirectory() {
        if (isExternalStorageWritable()) {
            // Get the directory for the USER's public pictures directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), App.getContext().getString(R.string.app_name));
            if (!file.mkdirs()) {
                Log.i("UTILS", "Public Directory not created");
            }
            Settings.APP_PICTURE_DIRECTORY = file.getPath();
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
            Settings.DEVICE_WIDTH     = (double) displayMetrics.widthPixels;
            Settings.DEVICE_HEIGHT    = (double) displayMetrics.heightPixels;
            Settings.DEVICE_DENSITY   = displayMetrics.density;
        } catch (Exception ex) {
            Log.d("ERROR", "Unable to get device metrics");
        }
    }

    public static void openDefaultGalleryApp(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        activity.startActivityForResult(intent, Config.MEDIA_REQUEST_CODE_KITKAT);
    }

    // slide the view from below itself to the current position
    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(

                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(700);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        view.setVisibility(View.GONE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(700);
        animate.setFillAfter(true);
        view.startAnimation(animate);
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

    public static void toast(Context context, Enumerates.Message messageState, String message, int duration) {
        try {
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
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
        } catch(Exception ex) {}
    }

    public static void activityPreview(Context context, String url, String title, boolean isVideo){
        Intent i = new Intent(context, PreviewActivity.class);
        i.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Keys.URL, url);
        i.putExtra(Keys.NAME, title);
        i.putExtra(Keys.VIDEO, isVideo);
        context.startActivity(i);
    }

    public static ArrayList<LatLng> getMapData(Activity activity, int resource) {
        ArrayList<LatLng> list = new ArrayList<>();

        try {
            InputStream inputStream = activity.getResources().openRawResource(resource);
            String json = new Scanner(inputStream).useDelimiter("\\A").next();
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                double lat = object.getDouble("lat");
                double lng = object.getDouble("lng");
                list.add(new LatLng(lat, lng));
            }
        } catch (JSONException e) {
            Utils.toast(activity, Enumerates.Message.ERROR, "Problem reading list of locations.", Toast.LENGTH_LONG);
        }

        return list;
    }

    public static List<String> getCities(String state) {
        for (KeyValue item: Config.CITIES) {
            if (item.key.equals(state)) {
                return (List<String>) item.value;
            }
        }
        return new ArrayList<>();
    }

    public static int getStateIndex(String state) {
        return Config.STATES.indexOf(state);
    }

    public static int getCityIndex(String state, String city) {
        return getCities(state).indexOf(city);
    }

}
