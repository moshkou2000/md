package com.moshkou.md.configs;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permission {

    public static boolean ACCESS_NETWORK_STATE          = false;
    public static boolean EXTERNAL_STORAGE              = false;
    public static boolean ACCESS_FINE_LOCATION          = false;
    public static boolean READ_CONTACTS                 = false;
    public static boolean RECORD_AUDIO                  = false;
    public static boolean CAMERA                        = false;


    public static class Check {

        public static void ACCESS_NETWORK_STATE(Activity activity) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_NETWORK_STATE)) {
                    // TODO: display "app needs ACCESS_NETWORK_STATE permissions to continue"
                }
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.ACCESS_NETWORK_STATE },
                        RequestCode.ACCESS_NETWORK_STATE);
            } else {
                Permission.ACCESS_NETWORK_STATE = true;
            }
        }

        public static void EXTERNAL_STORAGE(Activity activity) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // TODO: display "app needs READ_EXTERNAL_STORAGE permissions to continue"
                }
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        RequestCode.EXTERNAL_STORAGE);
            } else {
                Permission.EXTERNAL_STORAGE = true;
            }
        }

        public static void LOCATION(Activity activity) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // TODO: display "app needs ACCESS_FINE_LOCATION permissions to continue"
                }
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        RequestCode.ACCESS_FINE_LOCATION);
            } else {
                Permission.ACCESS_FINE_LOCATION = true;
            }
        }

        public static void READ_CONTACTS(Activity activity) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    // TODO: display "app needs ACCESS_FINE_LOCATION permissions to continue"
                }
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.READ_CONTACTS },
                        RequestCode.READ_CONTACTS);
            } else {
                Permission.READ_CONTACTS = true;
            }
        }

        public static void RECORD_AUDIO(Activity activity) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                    // TODO: display "app needs ACCESS_FINE_LOCATION permissions to continue"
                }
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.RECORD_AUDIO },
                        RequestCode.RECORD_AUDIO);
            } else {
                Permission.RECORD_AUDIO = true;
            }
        }

        public static void CAMERA(Activity activity) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    // TODO: display "app needs ACCESS_FINE_LOCATION permissions to continue"
                }
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.CAMERA },
                        RequestCode.CAMERA);
            } else {
                Permission.CAMERA = true;
            }
        }

    }

}
