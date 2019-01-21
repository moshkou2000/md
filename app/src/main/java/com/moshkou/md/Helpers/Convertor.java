package com.moshkou.md.Helpers;


import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;


public class Convertor {

    public static String toString(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return parsed;
    }

    public static JSONObject toJSONObject(NetworkResponse response) {
        try {
            return new JSONObject(toString(response));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray toJSONArray(NetworkResponse response) {
        try {
            return new JSONArray(toString(response));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // sample input
    // Type >> new TypeToken<ArrayList<PlayerModel>>(){}.getType();
    public static Object toObject(NetworkResponse response, Type type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(toString(response), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
