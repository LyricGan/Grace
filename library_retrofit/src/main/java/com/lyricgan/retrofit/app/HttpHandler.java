package com.lyricgan.retrofit.app;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

class HttpHandler<T> extends Handler {
    private final WeakReference<T> mReferenceObject;
    private OnMessageCallback mCallback;

    public HttpHandler(T object) {
        this.mReferenceObject = new WeakReference<T>(object);
    }

    public HttpHandler(T object, Looper looper) {
        super(looper);
        this.mReferenceObject = new WeakReference<T>(object);
    }

    public T get() {
        return mReferenceObject.get();
    }

    public void setCallback(OnMessageCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mCallback != null) {
            mCallback.callback(msg);
        }
    }

    public interface OnMessageCallback {

        void callback(Message msg);
    }
}
