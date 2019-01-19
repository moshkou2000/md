package com.moshkou.md.Services;


import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.moshkou.md.Configs.StatusCodes;

import java.lang.reflect.Type;


public class Success implements Response.Listener<NetworkResponse> {


    @Override
    public void onResponse(NetworkResponse response) {
        Log.i("MY_ERROR", response.toString());

        switch (response.statusCode) {

            case StatusCodes._200:
                break;
            case StatusCodes._201:
                break;
            case StatusCodes._202:
                break;
            case StatusCodes._203:
                break;
            case StatusCodes._204:
                break;
            case StatusCodes._205:
                break;
            case StatusCodes._206:
                break;

            case StatusCodes._100:
                break;
            case StatusCodes._101:
                break;

            case StatusCodes._300:
                break;
            case StatusCodes._301:
                break;
            case StatusCodes._302:
                break;
            case StatusCodes._303:
                break;
            case StatusCodes._304:
                break;
            case StatusCodes._305:
                break;
            case StatusCodes._307:
                break;
        }

        //Type type = new TypeToken<ArrayList<PlayerModel>>(){}.getType();
    }

}
