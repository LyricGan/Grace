package com.lyric.arch.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.lyric.arch.R;

import java.util.List;

/**
 * 常用工具类
 * @author lyricgan
 * @date 2017/11/24 11:38
 */
public class CommonUtils {

    private CommonUtils() {
    }

    /**
     * 判断是否在主线程
     * @return true or false
     */
    public static boolean isMainThread() {
        return (Looper.myLooper() == Looper.getMainLooper());
    }

    /**
     * 判断是否在子线程
     * @return true or false
     */
    public static boolean isBackgroundThread() {
        return !isMainThread();
    }

    /**
     * 检查权限是否已被允许
     * @param context 上下文
     * @param permission 应用权限名称
     * @return true or false
     */
    public static boolean checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
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
            if (runningTaskInfo.topActivity.getPackageName().equals(packageName)) {
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
                    if (tasks != null && !tasks.isEmpty()) {
                        ComponentName topActivity = tasks.get(0).topActivity;
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
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String className = cn.getClassName();
            if (!TextUtils.isEmpty(className) && className.equals(activityCls.getName())) {
                return true;
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return false;
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
     * 判断是否为主进程
     * @param context 上下文
     * @param processName 进程名称
     * @return true or false
     */
    public static boolean isMainProcess(Context context, String processName) {
        String packageName = context.getPackageName();
        return TextUtils.equals(packageName, processName);
    }

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        if (CommonUtils.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return tm.getImei();
            } else {
                return tm.getDeviceId();
            }
        }
        return null;
    }

    /**
     * 判断gps是否打开，需要权限{@link Manifest.permission#ACCESS_FINE_LOCATION}
     * @param context 上下文
     * @return true or false
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
    }

    /**
     * 复制文本到剪贴板
     * @param context 上下文
     * @param label 显示文本
     * @param text 实际复制文本
     * @return true or false
     */
    public static boolean clipText(Context context, CharSequence label, CharSequence text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            ClipData clipData = ClipData.newPlainText(label, text);
            clipboardManager.setPrimaryClip(clipData);
            return true;
        }
        return false;
    }

    /**
     * 获取剪贴板的复制文本
     * @param context 上下文
     * @return 字符序列数组，包含显示文本和实际复制文本
     */
    public static CharSequence[] getClipboardText(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            if (clipboardManager.hasPrimaryClip()) {
                CharSequence[] charSequences = new CharSequence[2];
                ClipData clipData = clipboardManager.getPrimaryClip();
                ClipDescription clipDescription = clipData.getDescription();
                if (clipDescription != null) {
                    CharSequence label = clipDescription.getLabel();
                    charSequences[0] = label;
                }
                if (clipData.getItemCount() > 0) {
                    CharSequence text = clipData.getItemAt(0).getText();
                    charSequences[1] = text;
                }
                return charSequences;
            }
        }
        return null;
    }

    /**
     * 创建快捷方式
     * @param context 上下文对象
     */
    public void createShortcut(Context context, String appName, int resourceId, Class<?> cls) {
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, Intent.ShortcutIconResource.fromContext(context, resourceId));
        // 设置为不允许重复创建
        intent.putExtra("duplicate", false);

        Intent launchIntent = new Intent(Intent.ACTION_MAIN);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        launchIntent.setClass(context, cls);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);

        context.sendBroadcast(intent);
    }

    /**
     * 删除程序的快捷方式
     *
     * @param context  上下文对象
     * @param activity Activity
     */
    public void deleteShortcut(Context context, Activity activity) {
        Intent shortCutIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
        String name = context.getPackageName();
        String appClass = name + "." + activity.getLocalClassName();
        ComponentName componentName = new ComponentName(name, appClass);
        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(componentName));
        context.sendBroadcast(shortCutIntent);
    }

    /**
     * 判断应用快捷方式是否创建
     *
     * @param context 上下文对象
     * @return 应用快捷方式是否创建
     */
    public boolean isShortcutCreated(Context context, String appName) {
        boolean isShortCutCreated = false;
        final ContentResolver cr = context.getContentResolver();
        final String AUTHORITY = "com.android.launcher2.settings";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        Cursor cursor = null;
        try {
            cursor = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?",
                    new String[]{appName}, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 判断游标是否为空
            if (cursor != null && cursor.getCount() > 0) {
                isShortCutCreated = true;
                cursor.close();
            }
        }
        return isShortCutCreated;
    }

}
