package com.call.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.call.app.utils.Prefs;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by RAVI on 1/26/2017.
 */

public class CallAppApplication extends Application {
    private static RequestQueue mRequestQueue;
    static CallAppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Prefs.init(CallAppApplication.this);
    }

    public static CallAppApplication getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void addToRequestQueue(StringRequest stringRequest) {
        getRequestQueue().add(stringRequest);

    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
