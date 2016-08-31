package com.lyric.grace.base;

import android.app.Application;

import com.lyric.grace.fresco.ImageHelper;
import com.lyric.grace.library.utils.LogUtils;
import com.lyric.grace.utils.StethoUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author lyricgan
 * @description
 * @time 2015/10/7 14:04
 */
public class BaseApp extends Application {
    private static BaseApp mInstance;
    private static RefWatcher mRefWatcher;

	@Override
	public void onCreate() {
		super.onCreate();
        mInstance = this;

        LogUtils.setDebug(Constants.DEBUG);
        initRefWatcher(Constants.LEAK_DEBUG);
        StethoUtils.initialize(this, Constants.DEBUG);
        ImageHelper.getInstance().initialize(this);
	}

	public static BaseApp getContext() {
		return mInstance;
	}

    public static RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    private void initRefWatcher(boolean isDebug) {
        if (isDebug) {
            mRefWatcher = LeakCanary.install(this);
        } else {
            mRefWatcher = RefWatcher.DISABLED;
        }
    }
}
