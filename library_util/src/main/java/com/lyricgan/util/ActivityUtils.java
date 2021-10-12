package com.lyricgan.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;

import java.util.List;

/**
 * 应用活动工具类
 * @author Lyric Gan
 */
public class ActivityUtils {

    public static boolean isActivityInvalid(Activity activity) {
        if (activity == null) {
            return true;
        }
        boolean invalid;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            invalid = activity.isDestroyed();
        } else  {
            invalid = activity.isFinishing();
        }
        return invalid;
    }

    public static void finishActivity(Activity activity) {
        if (isActivityInvalid(activity)) {
            return;
        }
        activity.finish();
    }

    /**
     * 判断应用是否启动
     * @param context Context
     * @param packageName the package name of app
     * @return true or false
     */
    public static boolean isAppRunning(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        final int maxRunningTaskNum = 200;
        List<ActivityManager.RunningTaskInfo> runningTaskList = activityManager.getRunningTasks(maxRunningTaskNum);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskList) {
            ComponentName componentName = getTopActivity(runningTaskInfo);
            if (componentName == null) {
                continue;
            }
            if (componentName.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否处于前台，需要同时满足两个条件：1、运行的进程中有当前应用进程，2、顶部activity对应的包与当前应用包名一致<br/>
     * 需要权限android.Manifest.permission.GET_TASKS
     * @param context Context
     * @param packageName the package name of app
     * @return true or false
     */
    public static boolean isAppOnForeground(Context context, String packageName) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager == null) {
            return false;
        }
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager == null) {
            return false;
        }
        // 判断是否为锁屏状态
        boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
        // 判断屏幕是否点亮
        boolean isScreenOff = !powerManager.isScreenOn();
        if (isLockedState || isScreenOff) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        // 获取所有正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null || appProcesses.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (TextUtils.equals(appProcess.processName, packageName)) {
                // 判断是否位于后台
                boolean isBackground = (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE);
                if (isBackground) {
                    return false;
                } else {
                    List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
                    ComponentName topActivity = null;
                    if (tasks != null && !tasks.isEmpty()) {
                        topActivity = getTopActivity(tasks.get(0));
                    }
                    if (topActivity != null) {
                        return topActivity.getPackageName().equals(packageName);
                    }
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断指定的activity是否正在显示
     * @param context Context
     * @param activityCls 指定的activity
     * @return true or false
     */
    public static boolean isActivityOnTop(Context context, Class<? extends Activity> activityCls) {
        if (!isAppOnForeground(context, context.getPackageName())) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        try {
            ActivityManager.RunningTaskInfo runningTaskInfo = am.getRunningTasks(1).get(0);
            ComponentName topActivity = getTopActivity(runningTaskInfo);
            if (topActivity == null) {
                return false;
            }
            String className = topActivity.getClassName();
            if (!TextUtils.isEmpty(className) && className.equals(activityCls.getName())) {
                return true;
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private static ComponentName getTopActivity(ActivityManager.RunningTaskInfo runningTaskInfo) {
        try {
            return runningTaskInfo.topActivity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void startActivity(Context context, Class<?> cls) {
        startActivity(context, cls, null);
    }

    public static void startActivity(Context context, Class<?> cls, Bundle extras) {
        startActivity(context, cls, extras, 0);
    }

    public static void startActivity(Context context, Class<?> cls, Bundle extras, int flags) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        if (flags > 0) {
            intent.setFlags(flags);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls) {
        startActivityForResult(activity, cls, 0);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode) {
        startActivityForResult(activity, cls, requestCode, null);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode, Bundle extras) {
        startActivityForResult(activity, cls, requestCode, extras, 0);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode, Bundle extras, int flags) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        if (flags > 0) {
            intent.setFlags(flags);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> cls) {
        startActivityForResult(fragment, cls, 0);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> cls, int requestCode) {
        startActivityForResult(fragment, cls, requestCode, null);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> cls, int requestCode, Bundle extras) {
        startActivityForResult(fragment, cls, requestCode, extras, 0);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> cls, int requestCode, Bundle extras, int flags) {
        if (fragment == null || fragment.getActivity() == null) {
            return;
        }
        Intent intent = new Intent(fragment.getActivity(), cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        if (flags > 0) {
            intent.setFlags(flags);
        }
        fragment.startActivityForResult(intent, requestCode);
    }
}
