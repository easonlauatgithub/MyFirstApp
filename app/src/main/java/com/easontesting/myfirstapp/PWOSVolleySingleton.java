package com.easontesting.myfirstapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class PWOSVolleySingleton {
    private static final String tag = "VolleySingleton";
    private static PWOSVolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private PWOSVolleySingleton(Context context) {
        Log.w(tag, "easontesting "+tag+" VolleySingleton 1, context:"+context);
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }
    public static synchronized PWOSVolleySingleton getInstance(Context context) {
        Log.w(tag, "easontesting "+tag+" getInstance 1");
        if (mInstance == null) {
            mInstance = new PWOSVolleySingleton(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        Log.w(tag, "easontesting "+tag+" getRequestQueue 1");
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            //Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        Log.w(tag, "easontesting "+tag+" addToRequestQueue 1");
        getRequestQueue().add(req);
    }
}