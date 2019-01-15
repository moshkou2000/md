package com.moshkou.md.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.moshkou.md.Configs.Status;
import com.moshkou.md.Controls.ConnectivityControl;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityControl.connection(context) == Status.Connectivity.NO_CONNECTIVITY) {
            Log.e("Receiver ", "not connction");// your code when internet lost
        } else {
            Log.e("Receiver ", "connected to internet");//your code when internet connection come back
        }
    }

}