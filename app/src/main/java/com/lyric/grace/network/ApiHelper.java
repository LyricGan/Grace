package com.lyric.grace.network;

import com.lyric.grace.common.Common;
import com.lyric.grace.utils.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类，封装Retrofit
 * @author lyricgan
 * @date 2016/7/26 16:34
 */
public class ApiHelper {
    private static final long CONNECT_TIMEOUT = 30L;
    private static final long READ_TIMEOUT = 30L;
    private static final long WRITE_TIMEOUT = 30L;
    private static final long MAX_CACHE_SIZE = 100 * 1024 * 1024L;
    private static final String CACHE_NAME = "cache_network";
    private static Retrofit mRetrofit;

    private ApiHelper() {
    }

    private static class ApiHelperHolder {
        private static final ApiHelper mInstance = new ApiHelper();
    }

    public static ApiHelper getInstance() {
        return ApiHelperHolder.mInstance;
    }

    private OkHttpClient buildDefaultClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.cache(new Cache(new File(getDefaultCacheDir()), MAX_CACHE_SIZE));
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(InterceptorHelper.getInstance().getParamsInterceptor());
        builder.addInterceptor(InterceptorHelper.getInstance().getCacheInterceptor());
        builder.addNetworkInterceptor(InterceptorHelper.getInstance().getCacheInterceptor());
        return builder.build();
    }

    private Retrofit buildDefaultRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiPath.BASE_URL)
                    .client(buildDefaultClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private String getDefaultCacheDir() {
        return FileUtils.getRootDirectory(Common.getContext()) + "/Android/data/" + Common.getContext().getPackageName() + File.separator + CACHE_NAME;
    }

    public <T> T build(Class<T> cls) {
        return build(buildDefaultRetrofit(), cls);
    }

    public <T> T build(Retrofit retrofit, Class<T> cls) {
        if (retrofit == null) {
            retrofit = buildDefaultRetrofit();
        }
        return retrofit.create(cls);
    }
}
