package com.moshkou.md.Services;


import com.android.volley.Request;
import com.moshkou.md.App;
import com.moshkou.md.Configs.Config;

import org.json.JSONObject;


public class Auth {


    public static void testPost(JSONObject param) {
        Requests request = new Requests(
                Request.Method.POST,
                Config.LOGIN_URL,
                null,
                param,
                new Success(),
                new Error()
        );
        App.getInstance().addToRequestQueue(request, "API_NAME as TAG");
    }


}
