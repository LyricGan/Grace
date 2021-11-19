package com.lyricgan.test.app;

public abstract class DefaultCallback<T> implements ResponseCallback<T> {

    @Override
    public void onSuccess(T response) {
    }

    @Override
    public void onFailed(ResponseError error) {
    }
}
