package com.call.app;

import android.app.Application;

import com.call.app.utils.Prefs;

/**
 * Created by RAVI on 1/26/2017.
 */

public class CallAppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.init(CallAppApplication.this);
    }
}
