package com.lyricgan.conn;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

public class DataLoader<T> {
    private final Method mMethod;
    private final String mUrl;
    private final Map<String, String> mParams;
    private final Map<String, File> mFileParams;
    private final ResponseCallback<T> mCallback;
    private Request<T> mRequest;

    public DataLoader(String url, Map<String, String> params, ResponseCallback<T> callback) {
        this(Method.POST, url, params, callback);
    }

    public DataLoader(Method method, String url, Map<String, String> params, ResponseCallback<T> callback) {
        this(method, url, params, null, callback);
    }

    public DataLoader(Method method, String url, Map<String, String> params, Map<String, File> fileParams, ResponseCallback<T> callback) {
        this.mMethod = method;
        this.mUrl = url;
        this.mParams = params;
        this.mFileParams = fileParams;
        this.mCallback = callback;
    }

    public void load() {
        load(true);
    }

    public void load(boolean isRefresh) {
        mRequest = new Request<>(mMethod, mUrl, mParams, mFileParams,  mCallback);
        mRequest.execute(isRefresh);
    }

    public void cancel() {
        mRequest.cancel();
    }

    public Method getMethod() {
        return this.mMethod;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public Map<String, String> getParams() {
        return this.mParams;
    }
}
