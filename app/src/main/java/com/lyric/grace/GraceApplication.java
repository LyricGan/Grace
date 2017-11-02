package com.lyric.grace;

import android.app.Application;

import com.lyric.grace.common.Constants;
import com.lyric.grace.utils.LogUtils;

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

        LogUtils.setDebug(Constants.DEBUG);
	}

	public static GraceApplication getContext() {
		return mInstance;
	}
}
