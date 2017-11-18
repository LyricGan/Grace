package com.lyric.grace.network;

import android.os.Build;
import android.text.TextUtils;

import com.lyric.grace.common.Common;
import com.lyric.grace.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器帮助类
 * @author lyricgan
 * @date 2016/8/2 17:54
 */
public class InterceptorHelper {
    private static final InterceptorHelper INSTANCE = new InterceptorHelper();
    private final Interceptor PARAMS_INTERCEPTOR = new ParamsInterceptor();
    private final Interceptor CACHE_INTERCEPTOR = new CacheInterceptor();

    private InterceptorHelper() {
    }

    public static InterceptorHelper getInstance() {
        return INSTANCE;
    }

    public Interceptor getParamsInterceptor() {
        return PARAMS_INTERCEPTOR;
    }

    public Interceptor getCacheInterceptor() {
        return CACHE_INTERCEPTOR;
    }

    /**
     * 网络缓存拦截器
     * 配置Cache-Control同时配置{@link #HEADER_USER_CACHE_TYPE}，若只配置Cache-Control则默认使用{@link #TYPE_NETWORK_WITH_CACHE}
     */
    private static class CacheInterceptor implements Interceptor {
        private static final String HEADER_CACHE_CONTROL = "Cache-Control";
        private static final String HEADER_PRAGMA = "Pragma";
        private static final String CACHE_CONTROL_ONLY_CACHE = "public, only-if-cached, max-age=2419200";
        private static final String CACHE_CONTROL_NO_CACHE = "public, max-age=1";
        private static final String HEADER_USER_CACHE_TYPE = "User-Cache-Type";
        // 断网情况下，加载缓存，联网情况下，优先加载缓存，默认情况
        public static final String TYPE_NETWORK_WITH_CACHE = "network_with_cache";
        // 断网情况下，加载缓存，联网情况下，只加载网络
        public static final String TYPE_NETWORK_NO_CACHE = "network_no_cache";

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                return chain.proceed(request);
            }
            String cacheType = request.header(HEADER_USER_CACHE_TYPE);
            if (!isNetworkConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (!isNetworkConnected()) {
                cacheControl = CACHE_CONTROL_ONLY_CACHE;
            } else if (TYPE_NETWORK_NO_CACHE.equals(cacheType)) {
                cacheControl = CACHE_CONTROL_NO_CACHE;
            }
            return originalResponse.newBuilder()
                    .header(HEADER_CACHE_CONTROL, cacheControl)
                    .removeHeader(HEADER_PRAGMA)
                    .build();
        }

        private boolean isNetworkConnected() {
            return NetworkUtils.isNetworkAvailable(Common.getContext());
        }
    }

    /**
     * 网络请求参数拦截器
     */
    private static class ParamsInterceptor implements Interceptor {

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
}
