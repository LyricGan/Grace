package com.lyric.grace.common;

import com.lyric.grace.BuildConfig;

/**
 * 应用常量接口
 * 
 * @author lyricgan
 * @created 2015-4-20
 */
public interface Constants {
    boolean DEBUG = BuildConfig.LOG_DEBUG;

    String EXTRAS_ID = "_id";
    String EXTRAS_TITLE = "_title";
    String EXTRAS_NAME = "_name";
    String EXTRAS_TYPE = "_type";
    String EXTRAS_DATA = "_data";

    String GBK = "GBK";
    String UTF_8 = "UTF_8";
}
