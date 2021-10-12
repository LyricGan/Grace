package com.lyricgan.retrofit.app;

public interface ResponseCallback<T> {

    void onSuccess(T response);

    void onFailed(ResponseError error);
}
