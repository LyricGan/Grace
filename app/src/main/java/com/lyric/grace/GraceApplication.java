package com.lyric.grace;

import android.app.Application;
import android.content.Context;

public class GraceApplication extends Application {
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
