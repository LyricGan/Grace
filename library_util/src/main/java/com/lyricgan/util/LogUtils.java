package com.lyricgan.util;

import android.util.Log;

import java.util.Hashtable;

/**
 * 日志工具类
 * @author Lyric Gan
 */
public class LogUtils {
    private static final String TAG = LogUtils.class.getSimpleName();
    private static boolean sDebug = false;
    private static int sLevel = Log.VERBOSE;
    private final String mClassName;
    private static final Hashtable<String, LogUtils> CACHE = new Hashtable<>();

    private LogUtils(String name) {
        this.mClassName = name;
    }

    private static LogUtils getInstance(String className) {
        LogUtils classLogger = CACHE.get(className);
        if (classLogger == null) {
            classLogger = new LogUtils(className);
            CACHE.put(className, classLogger);
        }
        return classLogger;
    }

    public static void setDebug(boolean flag) {
        sDebug = flag;
    }

    public static void setLevel(int level) {
        sLevel = level;
    }

    public static void i(String className, Object obj) {
        getInstance(className).i(obj);
    }

    public static void d(String className, Object obj) {
        getInstance(className).d(obj);
    }

    public static void w(String className, Object obj) {
        getInstance(className).w(obj);
    }

    public static void e(String className, Object obj) {
        getInstance(className).e(obj);
    }

    public static void e(String className, Exception exception) {
        getInstance(className).e(exception);
    }

    public static void e(String className, String log, Throwable tr) {
        getInstance(className).e(log, tr);
    }

    public static void v(String className, Object obj) {
        getInstance(className).v(obj);
    }

    private void i(Object obj) {
        if (sDebug && sLevel <= Log.INFO) {
            Log.i(TAG, getLogMessage(obj));
        }
    }

    private void d(Object obj) {
        if (sDebug && sLevel <= Log.DEBUG) {
            Log.d(TAG, getLogMessage(obj));
        }
    }

    private void v(Object obj) {
        if (sDebug && sLevel <= Log.VERBOSE) {
            Log.v(TAG, getLogMessage(obj));
        }
    }

    private void w(Object obj) {
        if (sDebug && sLevel <= Log.WARN) {
            Log.w(TAG, getLogMessage(obj));
        }
    }

    private void e(Object obj) {
        if (sDebug && sLevel <= Log.ERROR) {
            Log.e(TAG, getLogMessage(obj));
        }
    }

    private void e(Exception e) {
        if (sDebug && sLevel <= Log.ERROR) {
            String stackString = buildStackTrace();
            if (stackString != null) {
                Log.e(TAG, stackString + e);
            } else {
                Log.e(TAG, "error", e);
            }
        }
    }

    private void e(String log, Throwable tr) {
        if (sDebug && sLevel <= Log.ERROR) {
            String stackString = buildStackTrace();
            if (stackString != null) {
                Log.e(TAG, stackString + log);
            } else {
                Log.e(TAG, "error", tr);
            }
        }
    }

    private String getLogMessage(Object obj) {
        String stackString = buildStackTrace();
        if (stackString != null) {
            return stackString + obj;
        }
        return obj.toString();
    }

    private String buildStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            if (element.isNativeMethod()) {
                continue;
            }
            if (element.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (element.getClassName().equals(this.getClass().getName())) {
                continue;
            }
            return mClassName + "." + element.getMethodName() + "()"
                    + "[Line:" + element.getLineNumber() + "]";
        }
        return null;
    }
}
