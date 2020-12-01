package com.lyric.grace.samples.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * 应用权限工具类
 *
 * @author Lyric Gan
 * @since 2020/12/1
 */
public class PermissionHelper {

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static void requestPermissions(Fragment fragment, int requestCode, String... permissions) {
        if (fragment == null) {
            return;
        }
        fragment.requestPermissions(permissions, requestCode);
    }
}
