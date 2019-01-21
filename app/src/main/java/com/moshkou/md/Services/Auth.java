package com.moshkou.md.Services;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.moshkou.md.App;
import com.moshkou.md.Configs.Config;
import com.moshkou.md.Helpers.Convertor;
import com.moshkou.md.Models.UserModel;

import org.json.JSONObject;

import java.util.ArrayList;


public class Auth {


    public static void create(JSONObject param) {
        Requests request = new Requests(
                Request.Method.POST,
                Config.LOGIN_URL,
                null,
                param,
                new Error(),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        // response.statusCode == 20*

                        // to jsonObject
                        Convertor.toJSONObject(response);
                    }
                }
        );
        App.getInstance().addToRequestQueue(request, "API_NAME as TAG");
    }


}
