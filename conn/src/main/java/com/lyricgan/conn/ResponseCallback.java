package com.lyricgan.conn;

public interface ResponseCallback<T> {

    void onSuccess(T response);

    void onFailed(ResponseError error);
}
