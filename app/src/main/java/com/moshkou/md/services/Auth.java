package com.moshkou.md.services;


import com.android.volley.Request;
import com.moshkou.md.App;
import com.moshkou.md.configs.Config;
import com.moshkou.md.helpers.Convertor;


public class Auth {


    public static void login(String param) {
        Requests request = new Requests(
                Request.Method.POST,
                Config.LOGIN_URL,
                null,
                param,
                new Error(),
                Convertor::toJSONObject // to jsonObject
        );
        App.getInstance().addToRequestQueue(request, "login");
    }


}
