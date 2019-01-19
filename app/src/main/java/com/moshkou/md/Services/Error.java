package com.moshkou.md.Services;

import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.moshkou.md.Configs.Data;
import com.moshkou.md.Configs.Enumerates;
import com.moshkou.md.Configs.StatusCodes;


public class Error implements Response.ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("MY_ERROR", error.toString());

        if (error instanceof NetworkError || error instanceof NoConnectionError) {
            Data.CONNECTIVITY = Enumerates.Connectivity.NO_CONNECTIVITY;
        } else {
            NetworkResponse response = error.networkResponse;

            if (response != null) {
                Log.i("MY_ERROR", ">>" + response.statusCode);

                switch (response.statusCode) {
                    case StatusCodes._400:
                        break;
                    case StatusCodes._401:
                        break;
                    case StatusCodes._402:
                        break;
                    case StatusCodes._403:
                        break;
                    case StatusCodes._404:
                        break;
                    case StatusCodes._405:
                        break;
                    case StatusCodes._406:
                        break;
                    case StatusCodes._407:
                        break;
                    case StatusCodes._408:
                        break;
                    case StatusCodes._409:
                        break;
                    case StatusCodes._410:
                        break;
                    case StatusCodes._411:
                        break;
                    case StatusCodes._412:
                        break;
                    case StatusCodes._413:
                        break;
                    case StatusCodes._414:
                        break;
                    case StatusCodes._415:
                        break;
                    case StatusCodes._416:
                        break;
                    case StatusCodes._417:
                        break;

                    case StatusCodes._500:
                        break;
                    case StatusCodes._501:
                        break;
                    case StatusCodes._502:
                        break;
                    case StatusCodes._503:
                        break;
                    case StatusCodes._504:
                        break;
                    case StatusCodes._505:
                        break;
                }
            }
        }


    }

}
