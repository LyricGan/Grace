package com.lyric.grace;

import android.app.Application;

import com.lyric.grace.common.Constants;
import com.lyric.grace.utils.LogUtils;

/**
 * @author lyricgan
 * @description 应用入口
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
