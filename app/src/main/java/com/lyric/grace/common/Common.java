package com.lyric.grace.common;

import android.content.Context;

import com.lyric.grace.GraceApplication;

/**
 * 常用帮助类
 * @author lyricgan
 * @date 17/11/18 下午7:28
 */
public class Common implements Constants {

    private Common() {
    }

    public static Context getContext() {
        return GraceApplication.getApplication();
    }
}
