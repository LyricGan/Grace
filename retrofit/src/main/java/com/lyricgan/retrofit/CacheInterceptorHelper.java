package com.lyricgan.retrofit;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

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
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
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
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
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
