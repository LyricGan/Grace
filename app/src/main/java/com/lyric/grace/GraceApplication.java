package com.lyric.grace;

import android.app.Application;

/**
 * 应用入口
 * @author lyricgan
 * @time 2015/10/7 14:04
 */
public class GraceApplication extends Application {
    private static GraceApplication mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
        mInstance = this;
	}

	public static GraceApplication getContext() {
		return mInstance;
	}
}
