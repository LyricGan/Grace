package com.lyric.grace;

import android.app.Application;

import com.lyric.grace.frame.Constants;
import com.lyric.grace.library.utils.LogUtils;

/**
 * @author lyricgan
 * @description
 * @time 2015/10/7 14:04
 */
public class Grace extends Application {
    private static Grace mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
        mInstance = this;

        LogUtils.setDebug(Constants.DEBUG);
	}

	public static Grace getContext() {
		return mInstance;
	}
}
