package com.lyricgan.grace.network.retrofit;

import android.content.Context;

import com.lyricgan.grace.network.retrofit.converter.GsonConverterFactory;
import com.lyricgan.grace.network.retrofit.interceptor.CacheInterceptorHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * network manager with retrofit
 * https://github.com/square/retrofit
 *
 * @author lyricgan
 */
public class RetrofitManager {
    /** default connect timeout */
    private static final long CONNECT_TIMEOUT = 30L;
    /** default read timeout */
    private static final long READ_TIMEOUT = 30L;
    /** default write timeout */
    private static final long WRITE_TIMEOUT = 120L;

    private Retrofit mRetrofit;

    private RetrofitManager() {
    }

    private static final class NetworkManagerHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return NetworkManagerHolder.INSTANCE;
    }

    private OkHttpClient buildDefaultClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(CacheInterceptorHelper.getInstance().getCacheInterceptor(context));
        builder.addNetworkInterceptor(CacheInterceptorHelper.getInstance().getCacheNetworkInterceptor(context));
        return builder.build();
    }

    public Retrofit getRetrofit(Context context, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(buildDefaultClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public <T> T build(Retrofit retrofit, Class<T> cls) {
        this.mRetrofit = retrofit;

        return retrofit.create(cls);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
