package com.lyricgan.grace.samples;

import android.app.Application;

import com.lyricgan.util.ApplicationUtils;
import com.lyricgan.util.LogUtils;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationUtils.setApplication(this);
        LogUtils.setDebug(true);
    }
}
