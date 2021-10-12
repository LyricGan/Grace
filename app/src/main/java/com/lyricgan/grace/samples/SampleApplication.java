package com.lyricgan.grace.samples;

import android.app.Application;
import android.content.Context;

public class SampleApplication extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
}
