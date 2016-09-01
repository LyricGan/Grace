package com.lyric.grace.base;

import android.app.Application;

import com.lyric.grace.library.utils.LogUtils;

/**
 * @author lyricgan
 * @description
 * @time 2015/10/7 14:04
 */
public class BaseApp extends Application {
    private static BaseApp mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
        mInstance = this;

        LogUtils.setDebug(Constants.DEBUG);
	}

	public static BaseApp getContext() {
		return mInstance;
	}
}
