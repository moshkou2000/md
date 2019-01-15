package com.moshkou.md.Services;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moshkou.md.App;

import org.json.JSONObject;

public class Auth {

    // TODO: test required *************************************
    //
    public void test() {
        String url = "http://my-json-feed";

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // "Response: " + response.toString()
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        App.getInstance().addToRequestQueue(jsonObjectRequest, "API_NAME as TAG");
    }
}
