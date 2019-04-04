package com.lyric.support.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Toast工具类
 * 
 * @author lyricgan
 * 
 */
public class ToastUtils {
	private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Toast mToast = null;
    private static final Object mSynObject = new Object();

    private ToastUtils() {
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId) {
        show(context, resId, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getString(resId), duration);
    }

    public static void show(final Context context, final CharSequence text, final int duration) {
        if (context == null) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (mSynObject) {
                    if (mToast != null) {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    } else {
                        mToast = Toast.makeText(context, text, duration);
                    }
                    mToast.show();
                }
            }
        });
    }

    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
