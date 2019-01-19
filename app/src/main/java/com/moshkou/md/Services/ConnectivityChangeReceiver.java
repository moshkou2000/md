package com.moshkou.md.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.moshkou.md.Controls.ConnectivityControl;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityControl.connection(context);
    }

}