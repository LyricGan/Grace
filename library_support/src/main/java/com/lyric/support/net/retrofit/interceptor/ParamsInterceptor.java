package com.lyric.support.net.retrofit.interceptor;

import android.os.Build;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * params interceptor
 *
 * @author lyricgan
 */
public class ParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = addDefaultParams(chain.request());
        return chain.proceed(request);
    }

    private Request addDefaultParams(Request request) {
        HttpUrl httpUrl = request.url().newBuilder()
                .addQueryParameter("sys_v", Build.VERSION.RELEASE)
                .addQueryParameter("sys_m", Build.MODEL)
                .build();
        return request.newBuilder().url(httpUrl).build();
    }
}
