package com.lyric.grace.network.interceptor;

import android.os.Build;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 默认网络请求参数拦截器
 * @author lyricgan
 * @time 2016/10/24 15:50
 */
public class ParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = addDefaultParams(chain.request());
        return chain.proceed(request);
    }

    /**
     * 添加默认请求参数
     * sys_p 手机系统类型
     * sys_v 客户端的系统版本(version)
     * sys_m 手机的型号(model)
     * @param request Request
     * @return 添加参数后的Request
     */
    private Request addDefaultParams(Request request) {
        HttpUrl httpUrl = request.url().newBuilder()
                .addQueryParameter("sys_p", "android")
                .addQueryParameter("sys_v", Build.VERSION.RELEASE)
                .addQueryParameter("sys_m", Build.MODEL)
                .build();
        return request.newBuilder().url(httpUrl).build();
    }
}
