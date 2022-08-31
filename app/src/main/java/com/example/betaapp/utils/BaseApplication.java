package com.example.betaapp.utils;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static BaseApplication application;

    // -------------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------------

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
        ConnectivityMonitor.getInstance().register(this);
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static Context getContext() {
        return application.getApplicationContext();
    }
}
