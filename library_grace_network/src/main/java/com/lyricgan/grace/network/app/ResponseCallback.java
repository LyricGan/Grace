package com.lyricgan.grace.network.app;

public interface ResponseCallback<T> {

    void onSuccess(T response);

    void onFailed(ResponseError error);
}
