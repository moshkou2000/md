package com.moshkou.md.controls;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.moshkou.md.configs.Settings;
import com.moshkou.md.configs.Enumerates;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectivityControl {

    public static Enumerates.Connectivity connection(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        Enumerates.Connectivity status = Enumerates.Connectivity.NO_CONNECTIVITY;

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                status = Enumerates.Connectivity.WIFI;
            else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                status = Enumerates.Connectivity.MOBILE;
        }

        Settings.CONNECTIVITY = status;

        return status;
    }


    // TODO: test required *****************************
    //
    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;

    }

}
