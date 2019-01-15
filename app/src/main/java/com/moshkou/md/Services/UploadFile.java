package com.moshkou.md.Services;

import android.util.Log;

import com.moshkou.md.Configs.Status;

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

    public void upload(final String selectedFilePath, final String tags) {

        final String MEDIA_CREATE_URL = "MEDIA_CREATE_URL";
        final String TOKEN = "EXAMPLE EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE";

        final String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
        final File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];
        final String fileType = "image/" + fileName.split("[.]")[1];
        final MediaType MEDIA_TYPE_PNG = MediaType.parse(fileType);

        final String callback = "/api/media/create";
        final String host = "sgdev.apptractive.com.my";
        final String port = "3020";

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
                .url(MEDIA_CREATE_URL)
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=" + boundary)
                .addHeader("cache-control", "no-cache")
                .addHeader("connection", "keep-alive")
                .addHeader("x-access-token", TOKEN)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("MEDIA_SERVICE", "Failure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.i("MEDIA_SERVICE", "Response: " + response);

                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    Log.i("MEDIA_SERVICE", "responseBody: " + responseBody);

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
