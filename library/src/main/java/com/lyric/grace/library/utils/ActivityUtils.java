package com.lyric.grace.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * @author ganyu
 * @description
 * @time 2016/1/20 14:41
 */
public class ActivityUtils {
    
    private ActivityUtils() {
    }

    public static void jumpActivity(Context context, Class<? extends Activity> cls) {
        jumpActivity(context, cls, null);
    }

    public static void jumpActivity(Context context, Class<? extends Activity> cls, Bundle bundle) {
        jumpActivity(context, cls, bundle, -1);
    }

    public static void jumpActivityForResult(Context context, Class<? extends Activity> cls, int requestCode) {
        jumpActivity(context, cls, null, requestCode);
    }

    public static void jumpActivityForResult(Context context, Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        jumpActivity(context, cls, bundle, requestCode);
    }

    private static void jumpActivity(Context context, Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode == -1) {
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } else {
            if (context instanceof FragmentActivity) {
                ((FragmentActivity) context).startActivityForResult(intent, requestCode);
            } else if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            }
        }
    }

    public static void jumpActivityForResult(Fragment fragment, Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        fragment.startActivityForResult(intent, requestCode, bundle);
    }

    public static void jumpActivity(Context context, String action) {
        Intent intent = new Intent(action);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void jumpActivity(Context context, String action, Uri uri) {
        Intent intent = new Intent(action, uri);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void jumpActivity(Context context, String action, Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void jumpActivity(Context context, Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void jumpMainActivity(Context context, Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
