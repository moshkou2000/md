package com.moshkou.md.services;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.moshkou.md.App;
import com.moshkou.md.configs.Messages;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.configs.StatusCodes;
import com.moshkou.md.helpers.Utils;

import java.io.UnsupportedEncodingException;


public class Error implements Response.ErrorListener {

    private static String TAG = "VOLLEY_ERROR";

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.i(TAG, "error: " + error.toString());

        if (error instanceof NoConnectionError || error instanceof NetworkError) {
            Settings.CONNECTIVITY = Enumerates.Connectivity.NO_CONNECTIVITY;
            Utils.toast(App.getContext(), Enumerates.Message.ERROR, Messages.NO_INTERNET, Toast.LENGTH_SHORT);
        } else {
            NetworkResponse response = error.networkResponse;

            if (response != null) {
                try {
                    Log.i(TAG, "statusCode: " + response.statusCode);
                    Log.i(TAG, "data: " + new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Utils.toast(App.getContext(), Enumerates.Message.ERROR, "" + response.statusCode, Toast.LENGTH_SHORT);

                switch (response.statusCode) {
                    case StatusCodes._400:  // Bad Request
                        break;
                    case StatusCodes._401:  // TODO: LOGIN
                        break;
                    case StatusCodes._402:  // Payment Required
                        break;
                    case StatusCodes._403:  // TODO: KILL
                        break;
                    case StatusCodes._404:  // Not Found
                        break;
                    case StatusCodes._405:  // Method Not Allowed
                        break;
                    case StatusCodes._406:  // Not Acceptable
                        break;
                    case StatusCodes._407:  // TODO: Auth
                        break;
                    case StatusCodes._408:  // Timeout
                        break;
                    case StatusCodes._409:  // Conflict
                        break;
                    case StatusCodes._410:  // Gone
                        break;
                    case StatusCodes._411:  // Length Required
                        break;
                    case StatusCodes._412:  // Precondition Failed
                        break;
                    case StatusCodes._413:  // Request Entity Too Large
                        break;
                    case StatusCodes._414:  // Request-URI Too Long
                        break;
                    case StatusCodes._415:  // Unsupported Media Type
                        break;
                    case StatusCodes._416:  // Requested Range Not Satisfiable
                        break;
                    case StatusCodes._417:  // Expectation Failed
                        break;

                    case StatusCodes._500:  // Internal Server Error
                        break;
                    case StatusCodes._501:  // Not Implemented
                        break;
                    case StatusCodes._502:  // Bad Gateway
                        break;
                    case StatusCodes._503:  // Service Unavailable
                        break;
                    case StatusCodes._504:  // Timeout
                        break;
                    case StatusCodes._505:  // HTTP Version Not Supported
                        break;
                }
            }
        }


    }

}
