package com.moshkou.md.Controls;

import android.content.Context;
import android.content.SharedPreferences;

import com.moshkou.md.Configs.Config;

public class SharedPreferencesControl {

    public static void set(Context context, String key, String value) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Config.APP_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.remove(key);
        editor.putString(key, value);
        editor.apply();
    }

    public static String get(Context context, String key) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Config.APP_NAME, context.MODE_PRIVATE);

        return mSharedPreferences.getString(key, "");
    }

    public static void remove(Context context, String key) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Config.APP_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.remove(key);
        editor.apply();
    }

    public static String getRemove(Context context, String key) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Config.APP_NAME, context.MODE_PRIVATE);
        String data = mSharedPreferences.getString(key, "");

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();

        return data;
    }

}
