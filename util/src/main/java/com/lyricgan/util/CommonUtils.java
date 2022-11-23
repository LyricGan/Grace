package com.lyricgan.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import java.util.List;

/**
 * 常用工具类
 * @author Lyric Gan
 */
public class CommonUtils {

    private CommonUtils() {
    }

    /**
     * 判断是否为主进程
     * @param context 上下文
     * @param processName 进程名称
     * @return true or false
     */
    public static boolean isMainProcess(Context context, String processName) {
        return TextUtils.equals(context.getPackageName(), processName);
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
