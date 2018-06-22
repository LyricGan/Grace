package com.lyric.retrofit.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.lyric.retrofit.Utils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Cache-Control
 * noCache ：不使用缓存，全部走网络
 * noStore ： 不使用缓存，也不存储缓存
 * onlyIfCached ： 只使用缓存
 * maxAge ：设置最大失效时间，失效则不使用
 * maxStale ：设置最大失效时间，失效则不使用
 * minFresh ：设置最小有效时间，失效则不使用
 *
 * @author lyricgan
 */
public class CacheInterceptorHelper {
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_PRAGMA = "Pragma";
    private static final String CACHE_CONTROL_ONLY_CACHE = "public, only-if-cached, max-age=2419200";
    private static final String CACHE_CONTROL_NO_CACHE = "public, max-age=0";
    private static final String HEADER_USER_CACHE_TYPE = "User-Cache-Type";
    private static final String TYPE_NETWORK_NO_CACHE = "network_no_cache";
    private static final CacheInterceptorHelper INSTANCE = new CacheInterceptorHelper();

    private CacheInterceptorHelper() {
    }

    public static CacheInterceptorHelper getInstance() {
        return INSTANCE;
    }

    public Interceptor getCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String originalCacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(originalCacheControl)) {
                    return chain.proceed(request);
                }
                if (!Utils.isNetworkConnected(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    public Interceptor getCacheNetworkInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String originalCacheControl = request.cacheControl().toString();
                String cacheType = request.header(HEADER_USER_CACHE_TYPE);
                if (TextUtils.isEmpty(originalCacheControl)) {
                    return chain.proceed(request);
                }
                Response originalResponse = chain.proceed(request);
                String cacheControl = originalCacheControl;
                if (!Utils.isNetworkConnected(context)) {
                    cacheControl = CACHE_CONTROL_ONLY_CACHE;
                } else if (TYPE_NETWORK_NO_CACHE.equals(cacheType)) {
                    cacheControl = CACHE_CONTROL_NO_CACHE;
                }
                return originalResponse.newBuilder()
                        .header(HEADER_CACHE_CONTROL, cacheControl)
                        .removeHeader(HEADER_PRAGMA)
                        .build();
            }
        };
    }
}
