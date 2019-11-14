package com.lyric.support.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * 主线程工具类
 * @author Lyric Gan
 * @since 2019-11-14
 */
public class MainThread {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private MainThread() {
    }

    public static Handler getHandler() {
        return HANDLER;
    }

    public static void run(@NonNull Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            HANDLER.post(runnable);
        }
    }

    public static boolean isMainThread() {
        return (Looper.myLooper() == Looper.getMainLooper());
    }
}
