package com.lyricgan.util;

import android.app.Notification;
import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用桌面角标工具类
 * @author Lyric Gan
 */
public class BadgeUtils {
    private static final String MANUFACTURER_HUAWEI = "Huawei";
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";
    private static final String MANUFACTURER_MEIZU = "Meizu";
    private static final String MANUFACTURER_LENOVO = "lenovo";
    private static final String MANUFACTURER_SONY = "Sony";
    private static final String MANUFACTURER_LG = "LG";

    private static final String PROVIDER_CONTENT_URI = "content://com.android.badge/badge";

    private static final Impl IMPL;

    static {
        String manufacturer = Build.MANUFACTURER;
        if (manufacturer.equalsIgnoreCase(MANUFACTURER_HUAWEI)) {
            IMPL = new ImplHuaWei();
        } else if (manufacturer.equalsIgnoreCase(MANUFACTURER_XIAOMI)) {
            IMPL = new ImplXiaoMi();
        } else if (manufacturer.equalsIgnoreCase(MANUFACTURER_VIVO)) {
            IMPL = new ImplVIVO();
        } else if (manufacturer.equalsIgnoreCase(MANUFACTURER_OPPO)) {
            IMPL = new ImplOPPO();
        } else if (manufacturer.equalsIgnoreCase(MANUFACTURER_SAMSUNG)) {
            IMPL = new ImplSamSung();
        } else if (manufacturer.equalsIgnoreCase(MANUFACTURER_MEIZU)) {
            IMPL = new ImplMeiZu();
        } else if (manufacturer.equalsIgnoreCase(MANUFACTURER_LENOVO)) {
            IMPL = new ImplLenovo();
        } else if (manufacturer.equalsIgnoreCase(MANUFACTURER_SONY)) {
            IMPL = new ImplSony();
        } else if (manufacturer.equalsIgnoreCase(MANUFACTURER_LG)) {
            IMPL = new ImplLG();
        } else {
            IMPL = new ImplDefault();
        }
    }

    public static void setBadgeCount(Context context, int badgeCount) {
        IMPL.setBadgeCount(context, badgeCount);
    }

    public static void clearBadgeCount(Context context) {
        setBadgeCount(context, 0);
    }

    interface Impl {

        void setBadgeCount(Context context, int badgeCount);
    }

    private static class ImplHuaWei implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
            try {
                if (badgeCount < 0) {
                    badgeCount = 0;
                }
                String packageName = context.getPackageName();
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                if (launchIntent == null) {
                    return;
                }
                ComponentName componentName = launchIntent.getComponent();
                if (componentName == null) {
                    return;
                }
                String launcherClassName = componentName.getClassName();
                Bundle bundle = new Bundle();
                bundle.putString("package", packageName);
                bundle.putString("class", launcherClassName);
                bundle.putInt("badgenumber", badgeCount);
                context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ImplXiaoMi implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {

        }

        public void setBadgeNumber(Notification notification, int badgeCount) {
            try {
                Field field = notification.getClass().getDeclaredField("extraNotification");
                Object extraNotification = field.get(notification);
                if (extraNotification == null) {
                    return;
                }
                Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
                method.invoke(extraNotification, badgeCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ImplVIVO implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
            try {
                String packageName = context.getPackageName();
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                if (launchIntent == null) {
                    return;
                }
                ComponentName componentName = launchIntent.getComponent();
                if (componentName == null) {
                    return;
                }
                String launcherClassName = componentName.getClassName();
                Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
                intent.putExtra("packageName", packageName);
                intent.putExtra("className", launcherClassName);
                intent.putExtra("notificationNum", badgeCount);
                context.sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ImplOPPO implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
            try {
                if (badgeCount == 0) {
                    badgeCount = -1;
                }
                Intent intent = new Intent("com.oppo.unsettledevent");
                intent.putExtra("pakeageName", context.getPackageName());
                intent.putExtra("badgeCount", badgeCount);
                intent.putExtra("upgradeNumber", badgeCount);

                PackageManager packageManager = context.getPackageManager();
                List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
                boolean canResolveBroadcast = receivers.size() > 0;
                if (canResolveBroadcast) {
                    context.sendBroadcast(intent);
                } else {
                    Bundle extras = new Bundle();
                    extras.putInt("app_badge_count", badgeCount);
                    context.getContentResolver().call(Uri.parse(PROVIDER_CONTENT_URI), "setAppBadgeCount", null, extras);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ImplSamSung implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
            try {
                String packageName = context.getPackageName();
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                if (launchIntent == null) {
                    return;
                }
                ComponentName componentName = launchIntent.getComponent();
                if (componentName == null) {
                    return;
                }
                String launcherClassName = componentName.getClassName();
                Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
                intent.putExtra("badge_count", badgeCount);
                intent.putExtra("badge_count_package_name", packageName);
                intent.putExtra("badge_count_class_name", launcherClassName);
                context.sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ImplMeiZu implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
        }
    }

    private static class ImplLenovo implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
            try {
                ArrayList<String> ids = new ArrayList<>();
                Bundle extra = new Bundle();
                extra.putStringArrayList("app_shortcut_custom_id", ids);
                extra.putInt("app_badge_count", badgeCount);
                context.getContentResolver().call(Uri.parse(PROVIDER_CONTENT_URI), "setAppBadgeCount", null, extra);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ImplSony implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
            String packageName = context.getPackageName();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent == null) {
                return;
            }
            ComponentName componentName = launchIntent.getComponent();
            if (componentName == null) {
                return;
            }
            String launcherClassName = componentName.getClassName();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("badge_count", badgeCount);
                contentValues.put("package_name", context.getPackageName());
                contentValues.put("activity_name", launcherClassName);
                AsyncQueryHandler asyncQueryHandler = new InnerAsyncQueryHandler(context.getContentResolver());
                asyncQueryHandler.startInsert(0, null, Uri.parse("content://com.sonymobile.home" + ".resourceprovider/badge"), contentValues);
            } catch (Exception e) {
                try {
                    Intent intent = new Intent("com.sonyericsson.home.action.UPDATE_BADGE");
                    intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", badgeCount > 0);
                    intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);
                    intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(badgeCount));
                    intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());
                    context.sendBroadcast(intent);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        private static class InnerAsyncQueryHandler extends AsyncQueryHandler {

            public InnerAsyncQueryHandler(ContentResolver cr) {
                super(cr);
            }
        }
    }

    private static class ImplLG implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
        }
    }

    private static class ImplDefault implements Impl {
        @Override
        public void setBadgeCount(Context context, int badgeCount) {
        }
    }
}
