package com.moshkou.md.services;

import android.util.Log;

import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Settings;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;



public class UploadFile {

    private static final String TAG = "UPLOAD_FILE";


    public static void upload(final String selectedFilePath, final String tags) {
        final String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
        final File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];
        final String fileType = "image/" + fileName.split("[.]")[1];
        final MediaType MEDIA_TYPE_PNG = MediaType.parse(fileType);

        final String callback = "/api/media/create";
        final String host = "sgdev.apptractive.com.my";
        final String port = "";

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("host", host)
                .addFormDataPart("port", port)
                .addFormDataPart("callback", callback)
                .addFormDataPart("tags", tags)
                .addFormDataPart("filename", fileName,
                        RequestBody.create(MEDIA_TYPE_PNG, selectedFile))
                .build();

        Request request = new Request.Builder()
                .url(Config.SG_CDN)
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=" + boundary)
                .addHeader("cache-control", "no-cache")
                .addHeader("connection", "keep-alive")
                .addHeader("x-access-token", Settings.USER.getSgToken())
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "Failure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.i(TAG, "Response: " + response);

                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    Log.i(TAG, "responseBody: " + responseBody);

                    try {
                        String data = responseBody.string();

                        if (data.length() == 0) {
                            // ERROR
                        } else {
                            // TODO: check data ************************************
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // ERROR
                    }

                } else {
                    // ERROR
                }
            }
        });
    }

}
