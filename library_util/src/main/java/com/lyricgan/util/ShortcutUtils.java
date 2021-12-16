package com.lyricgan.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

/**
 * 快捷方式工具类
 * @author Lyric Gan
 */
public class ShortcutUtils {

    private ShortcutUtils() {
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
