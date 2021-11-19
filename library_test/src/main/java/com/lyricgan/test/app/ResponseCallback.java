package com.lyricgan.test.app;

public interface ResponseCallback<T> {

    void onSuccess(T response);

    void onFailed(ResponseError error);
}
