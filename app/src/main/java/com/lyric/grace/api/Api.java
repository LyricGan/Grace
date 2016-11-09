package com.lyric.grace.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lyric.grace.api.interceptor.InterceptorHelper;
import com.lyric.grace.api.interceptor.OkHttpLogInterceptor;
import com.lyric.grace.frame.Constants;
import com.lyric.grace.library.utils.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author lyricgan
 * @description 网络请求类
 * @time 2016/7/26 16:34
 */
public class Api {
    private static final long CONNECT_TIMEOUT = 30L;
    private static final long READ_TIMEOUT = 30L;
    private static final long WRITE_TIMEOUT = 30L;
    private static final long MAX_CACHE_SIZE = 100 * 1024 * 1024L;
    private static final String CACHE_NAME = "cache_network";
    private static final String PACKAGE_NAME = "net.medlinker.crm";
    private static Api mInstance;
    private static Retrofit mRetrofit;

    private Api() {
    }

    public static synchronized Api getInstance() {
        if (mInstance == null) {
            mInstance = new Api();
        }
        return mInstance;
    }

    private OkHttpClient buildDefaultClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.cache(new Cache(new File(getDefaultCacheDir()), MAX_CACHE_SIZE));
        builder.retryOnConnectionFailure(true);
        if (Constants.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
            builder.addNetworkInterceptor(new OkHttpLogInterceptor(false));
        }
        builder.addInterceptor(InterceptorHelper.getInstance().getParamsInterceptor());
        builder.addInterceptor(InterceptorHelper.getInstance().getCacheInterceptor());
        builder.addNetworkInterceptor(InterceptorHelper.getInstance().getCacheInterceptor());
        return builder.build();
    }

    private void buildRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiPath.BASE_URL)
                .client(buildDefaultClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            buildRetrofit();
        }
        return mRetrofit;
    }

    private String getDefaultCacheDir() {
        return FileUtils.getRootDirectory() + "/Android/data/" + PACKAGE_NAME + File.separator + CACHE_NAME;
    }

    public <T> T build(Class<T> cls) {
        return build(getRetrofit(), cls);
    }

    public <T> T build(Retrofit retrofit, Class<T> cls) {
        if (retrofit == null) {
            throw new NullPointerException("retrofit is null");
        }
        return retrofit.create(cls);
    }
}
