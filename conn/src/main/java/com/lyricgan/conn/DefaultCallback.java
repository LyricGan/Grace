package com.lyricgan.conn;

public abstract class DefaultCallback<T> implements ResponseCallback<T> {

    @Override
    public void onSuccess(T response) {
    }

    @Override
    public void onFailed(ResponseError error) {
    }
}
