package com.moshkou.md.services;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import java.io.File;

public class DownloadFile extends AsyncTask<String, Integer, String> {

    private Context context;
    private Uri uri;
    private String filename;
    private String title = "APP_NAME";

    public DownloadFile(Context context, Uri uri, String filename) {
        this.context = context;
        this.uri = uri;
        this.filename = filename;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected String doInBackground(String... strings) {

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDescription("Downloading...");
        request.setTitle(title);
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(title, filename);

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        return title + File.separator + filename;
    }
}
