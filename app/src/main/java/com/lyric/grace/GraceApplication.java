package com.lyric.grace;

import android.app.Application;

/**
 * @author lyricgan
 * @date 2015/10/7 14:04
 */
public class GraceApplication extends Application {
    private static GraceApplication sApplication;

	@Override
	public void onCreate() {
		super.onCreate();
        sApplication = this;
	}

	public static GraceApplication getApplication() {
		return sApplication;
	}
}
