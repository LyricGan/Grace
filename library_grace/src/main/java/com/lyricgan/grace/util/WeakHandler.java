package com.lyricgan.grace.util;

import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

/**
 * Handler，采用弱引用，防止内存泄漏
 * @author Lyric Gan
 * @since 2019-12-07
 */
public class WeakHandler<T> extends Handler {
    private WeakReference<T> mReference;

    public WeakHandler(T object) {
        this(object, Looper.getMainLooper());
    }

    public WeakHandler(T object, Looper looper) {
        super(looper);
        this.mReference = new WeakReference<>(object);
    }

    public T get() {
        if (mReference != null) {
            return mReference.get();
        }
        return null;
    }

    public void clear() {
        if (mReference != null) {
            mReference.clear();
        }
    }
}
