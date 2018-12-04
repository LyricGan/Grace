package com.lyric.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 网络请求响应回调接口
 * @author lyricgan
 */
public abstract class HttpResponseCallback<T> implements HttpCallback {
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onResponse(final HttpRequest httpRequest, HttpResponse httpResponse) {
        Response response = httpResponse.getResponse();
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            onFailure(httpRequest, new IOException("Response is null"));
            return;
        }
        final T result = parseResponse(responseBody);
        if (result == null) {
            onFailure(httpRequest, new IOException("Response parse error " + responseBody.toString()));
            return;
        }
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onResponse(httpRequest, result);
            }
        });
    }

    @Override
    public void onFailure(final HttpRequest httpRequest, final IOException e) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(httpRequest, e);
            }
        });
    }

    @Override
    public void onCancel(HttpRequest httpRequest) {
    }

    public abstract T parseResponse(ResponseBody responseBody);

    public abstract void onResponse(HttpRequest httpRequest, T result);

    public abstract void onFailed(HttpRequest httpRequest, IOException e);
}
