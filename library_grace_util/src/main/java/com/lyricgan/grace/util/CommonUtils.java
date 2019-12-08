package com.lyricgan.grace.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;

import java.util.List;

/**
 * 常用工具类
 * @author Lyric Gan
 * @since 2019-12-07
 */
public class CommonUtils {
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public static Handler getMainHandler() {
        return MAIN_HANDLER;
    }

    public static void run(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            MAIN_HANDLER.post(runnable);
        }
    }

    /**
     * 判断是否在主线程
     * @return true or false
     */
    public static boolean isMainThread() {
        return (Looper.myLooper() == Looper.getMainLooper());
    }

    /**
     * 判断是否为主进程
     * @param context 上下文
     * @param processName 进程名称
     * @return true or false
     */
    public static boolean isMainProcess(Context context, String processName) {
        String packageName = context.getPackageName();
        return TextUtils.equals(packageName, processName);
    }

    /**
     * 获取当前进程名称
     * @param context 上下文
     * @return 当前进程名称
     */
    public static String getCurrentProcessName(Context context) {
        String processName = "";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return processName;
        }
        List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
        if (processInfoList == null || processInfoList.isEmpty()) {
            return processName;
        }
        int pid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo.pid == pid) {
                processName = processInfo.processName;
                break;
            }
        }
        return processName;
    }

    /**
     * 判断服务是否已启动
     * @param context Context
     * @param serviceName the service name
     * @return true or false
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return false;
        }
        final int maxRunningServiceNum = 200;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningServiceInfo> runningServiceList = activityManager.getRunningServices(maxRunningServiceNum);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceList) {
            if (runningServiceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
