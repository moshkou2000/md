package com.moshkou.md;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.moshkou.md.controls.ConnectivityControl;

public class App extends Application {

    private static App ourInstance;
    private static Context context;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;


    public App() {}

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        setContext(getApplicationContext());

        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

        init();
    }
    public static App getInstance() {
        return ourInstance;
    }
    public static Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    public void cancelRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    private void init() {
        ConnectivityControl.connection(context);
    }

}
