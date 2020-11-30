package com.lyric.grace.samples.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * 应用电池及后台运行权限设置工具类，需要在manifest文件配置允许忽略电池优化权限
 *
 * @author Lyric Gan
 * @since 2020/11/27
 */
public class AppRunningUtils {

    public static boolean isIgnoringBatteryOptimizations(Context context) {
        if (context == null) {
            return false;
        }
        boolean isIgnoring = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (powerManager != null) {
                isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
            }
        }
        return isIgnoring;
    }

    public static void requestIgnoreBatteryOptimizations(Context context) {
        if (context == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void jumpBatteryOptimizationsSettings(Context context) {
        if (context == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void showActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    private static void showActivity(Context context, String packageName, String activityPath) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityPath));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void handleBackgroundRunningSettings(Context context) {
        if (context == null) {
            return;
        }
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) {
            return;
        }
        if ("huawei".equalsIgnoreCase(brand) || "honor".equalsIgnoreCase(brand)) {
            try {
                showActivity(context, "com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
            } catch (Exception e) {
                try {
                    showActivity(context, "com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if ("xiaomi".equalsIgnoreCase(brand)) {
            try {
                showActivity(context, "com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("oppo".equalsIgnoreCase(brand)) {
            try {
                showActivity(context, "com.coloros.phonemanager");
            } catch (Exception e1) {
                try {
                    showActivity(context, "com.oppo.safe");
                } catch (Exception e2) {
                    try {
                        showActivity(context, "com.coloros.oppoguardelf");
                    } catch (Exception e3) {
                        try {
                            showActivity(context, "com.coloros.safecenter");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if ("vivo".equalsIgnoreCase(brand)) {
            try {
                showActivity(context, "com.iqoo.secure");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("samsung".equalsIgnoreCase(brand)) {
            try {
                showActivity(context, "com.samsung.android.sm_cn");
            } catch (Exception e) {
                try {
                    showActivity(context, "com.samsung.android.sm");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if ("meizu".equalsIgnoreCase(brand)) {
            try {
                showActivity(context, "com.meizu.safe");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
