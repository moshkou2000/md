package com.moshkou.md.services;


import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.google.gson.Gson;
import com.moshkou.md.App;
import com.moshkou.md.configs.Config;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.helpers.Convertor;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnBillboardsListener;
import com.moshkou.md.interfaces.OnLoginListener;
import com.moshkou.md.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;


public class Auth {

    private static final String TAG = "AUTH";


    public static void login(OnLoginListener listener, JSONObject param) {
//        Requests request = new Requests(
//                Request.Method.POST,
//                Config.LOGIN_URL,
//                null,
//                param.toString(),
//                new Error(),
//                respond -> listener.onLoginInteraction(Convertor.toObject(respond))
//        );
//        App.getInstance().addToRequestQueue(request, "login");

        listener.onLoginInteraction(new UserModel());

        sgLogin();
    }


    public static void sgLogin() {
        JSONObject params = new JSONObject();
        try {
            params.put("email", Config.SG_LOGIN_EMAIL);
            params.put("password", Config.SG_LOGIN_PASSWORD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Requests request = new Requests(
                Request.Method.POST,
                Config.SG_LOGIN_URL,
                null,
                params.toString(),
                new Error(),
                Auth::sgLicense
        );
        App.getInstance().addToRequestQueue(request, "sgLogin");
    }

    private static void sgLicense(NetworkResponse data) {
        JSONObject d = Convertor.toJSONObject(data);
        JSONObject params = new JSONObject();
        try {
            params.put("license_key", Config.SG_LICENSE_KEY);
            params.put("user", d.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Requests request = new Requests(
                Request.Method.POST,
                Config.SG_LICENSE_URL,
                null,
                params.toString(),
                new Error(),
                respond -> {
                    try {
                        JSONObject a = Convertor.toJSONObject(respond);
                        assert a != null;
                        Settings.USER.setSgToken(a.getString("token_sg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        );
        App.getInstance().addToRequestQueue(request, "sgLicense");
    }
}
