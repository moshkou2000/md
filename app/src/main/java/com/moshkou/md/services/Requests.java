package com.moshkou.md.services;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class Requests extends Request<NetworkResponse> {


    private Map<String, String> headers = new ArrayMap<>();

    /** Default charset for JSON request. */
    private final static String PROTOCOL_CHARSET = "utf-8";

    /** Lock to guard listener as it is cleared on cancel() and read on delivery. */
    private final Object lock = new Object();

    @Nullable
    private final String params;

    @Nullable
    @GuardedBy("lock")
    private Response.Listener<NetworkResponse> listener;


    public Requests(
            int method,
            String url,
            @Nullable HashMap<String, String> headers,
            @Nullable String params,
            @Nullable Response.ErrorListener errorListener,
            @Nullable Response.Listener<NetworkResponse> listener) {
        super(method, url, errorListener);

        this.headers.put("Content-Type", "application/json");
//        this.headers.put("apiKey", "YourAppKey");
        if (headers != null)
            this.headers.putAll(headers);
        this.params = params;
        this.listener = listener;
    }

    @Override
    public void cancel() {
        super.cancel();
        synchronized (lock) {
            listener = null;
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        Response.Listener<NetworkResponse> listener;
        synchronized (lock) {
            listener = this.listener;
        }
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    @Override
    public byte[] getBody() {
        try {
            return params == null ? null : params.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf(
                    "Unsupported Encoding while trying to get the bytes of %s using %s",
                    params, PROTOCOL_CHARSET);
            return null;
        }
    }

}
