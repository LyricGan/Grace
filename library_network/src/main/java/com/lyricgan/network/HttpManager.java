package com.lyricgan.network;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * HTTP网络请求管理类，使用OkHttp实现
 * @author Lyric Gan
 */
public class HttpManager {
    private OkHttpClient mHttpClient;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    private static class Holder {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    private HttpManager() {
        mHttpClient = new OkHttpClient.Builder().build();
    }

    public static HttpManager getInstance() {
        return Holder.INSTANCE;
    }

    public void initHttpClient(OkHttpClient httpClient) {
        this.mHttpClient = httpClient;
    }

    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }

    public Handler getMainHandler() {
        return mMainHandler;
    }

    public void enqueuePost(Object tag, String url, Map<String, String> params, ResponseCallback callback) {
        enqueuePost(tag, url, null, params, callback);
    }

    public void enqueuePost(Object tag, String url, Map<String, String> headers, Map<String, String> params, ResponseCallback callback) {
        enqueuePost(mHttpClient, tag, url, headers, params, callback);
    }

    public void enqueuePost(OkHttpClient httpClient, Object tag, String url, Map<String, String> headers, Map<String, String> params, ResponseCallback callback) {
        if (httpClient == null) {
            return;
        }
        Request request = buildPostRequest(tag, url, headers, params);
        httpClient.newCall(request).enqueue(callback);
    }

    public void enqueuePostJson(Object tag, String url, String json, ResponseCallback callback) {
        OkHttpClient httpClient = mHttpClient;
        if (httpClient == null) {
            return;
        }
        Request request = new Request.Builder()
                .tag(tag)
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();
        httpClient.newCall(request).enqueue(callback);
    }

    public Response executePost(OkHttpClient httpClient, Object tag, String url, Map<String, String> headers, Map<String, String> params) {
        if (httpClient == null) {
            return null;
        }
        Request request = buildPostRequest(tag, url, headers, params);
        try {
            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void enqueueGet(Object tag, String url, Map<String, String> params, boolean useCache, ResponseCallback callback) {
        enqueueGet(tag, url, null, params, useCache, callback);
    }

    public void enqueueGet(Object tag, String url, Map<String, String> headers, Map<String, String> params, boolean useCache, ResponseCallback callback) {
        enqueueGet(mHttpClient, tag, url, headers, params, useCache, callback);
    }

    public void enqueueGet(OkHttpClient httpClient, Object tag, String url, Map<String, String> headers, Map<String, String> params, boolean useCache, ResponseCallback callback) {
        if (httpClient == null) {
            return;
        }
        Request request = buildGetRequest(tag, url, headers, params, useCache);
        httpClient.newCall(request).enqueue(callback);
    }

    public Response executeGet(OkHttpClient httpClient, Object tag, String url, Map<String, String> headers, Map<String, String> params, boolean useCache) {
        if (httpClient == null) {
            return null;
        }
        Request request = buildGetRequest(tag, url, headers, params, useCache);
        try {
            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void enqueueDownload(Object tag, String url, ResponseCallback callback) {
        enqueueGet(tag, url, null, null, false, callback);
    }

    public void cancelByTag(Object tag) {
        OkHttpClient httpClient = mHttpClient;
        if (httpClient == null) {
            return;
        }
        Dispatcher dispatcher = httpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public void cancelAll() {
        OkHttpClient httpClient = mHttpClient;
        if (httpClient == null) {
            return;
        }
        Dispatcher dispatcher = httpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            call.cancel();
        }
        for (Call call : dispatcher.runningCalls()) {
            call.cancel();
        }
    }

    public CookieJar getCookieJar() {
        OkHttpClient httpClient = mHttpClient;
        if (httpClient != null) {
            return httpClient.cookieJar();
        }
        return null;
    }

    private Headers buildHeaders(Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) {
            return builder.build();
        }
        String key, value;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            if (value == null) {
                value = "";
            }
            builder.add(key, value);
        }
        return builder.build();
    }

    private RequestBody buildFormBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params == null || params.isEmpty()) {
            return builder.build();
        }
        String key, value;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            if (value == null) {
                value = "";
            }
            builder.add(key, value);
        }
        return builder.build();
    }

    private String buildGetUrl(String url, Map<String, String> params) {
        if (TextUtils.isEmpty(url) || params == null || params.isEmpty()) {
            return url;
        }
        Uri uri = Uri.parse(url);
        if (uri == null) {
            return url;
        }
        Uri.Builder builder = uri.buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build().toString();
    }

    private CacheControl buildCacheControl(boolean useCache) {
        CacheControl.Builder builder = new CacheControl.Builder();
        if (useCache) {
            builder.onlyIfCached();
        } else {
            builder.noCache();
        }
        return builder.build();
    }

    private Request buildPostRequest(Object tag, String url, Map<String, String> headers, Map<String, String> params) {
        return new Request.Builder()
                .tag(tag)
                .url(url)
                .headers(buildHeaders(headers))
                .post(buildFormBody(params))
                .build();
    }

    private Request buildGetRequest(Object tag, String url, Map<String, String> headers, Map<String, String> params, boolean useCache) {
        return new Request.Builder()
                .tag(tag)
                .url(buildGetUrl(url, params))
                .headers(buildHeaders(headers))
                .cacheControl(buildCacheControl(useCache))
                .get()
                .build();
    }
}
