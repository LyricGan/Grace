package com.lyric.grace.network;

/**
 * @author lyricgan
 * @description
 * @time 2016/7/26 16:35
 */
public class ApiFactory {
    private static IServiceApi mServiceApi;

    private ApiFactory() {
    }

    private static Api getDefault() {
        return Api.getInstance();
    }

    public static IServiceApi getService() {
        if (mServiceApi == null) {
            mServiceApi = getDefault().build(IServiceApi.class);
        }
        return mServiceApi;
    }
}
