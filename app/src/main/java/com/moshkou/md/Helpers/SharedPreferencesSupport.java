package com.moshkou.md.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.moshkou.md.R;

import java.util.Set;


public class SharedPreferencesSupport {

    public static void setString(Context context, String key, String value) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.remove(key);
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), context.MODE_PRIVATE);

        return mSharedPreferences.getString(key, "");
    }


    public static void setStringArray(Context context, String key, Set<String> values) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.remove(key);
        editor.putStringSet(key, values);
        editor.apply();
    }

    public static Set<String> getStringArray(Context context, String key) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), context.MODE_PRIVATE);

        return mSharedPreferences.getStringSet(key, null);
    }


    public static void remove(Context context, String key) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.remove(key);
        editor.apply();
    }

}
