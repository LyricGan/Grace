package com.lyricgan.grace.samples.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * 页面跳转工具类
 * @author Lyric Gan
 */
public class PageHelper {

    private PageHelper() {
    }

    public static void jump(Context context, Class<? extends Activity> cls) {
        jump(context, cls, null);
    }

    public static void jump(Context context, Class<? extends Activity> cls, Bundle extras) {
        jump(context, cls, extras, 0);
    }

    public static void jump(Context context, Class<? extends Activity> cls, Bundle extras, int flags) {
        if (context == null) {
            return;
        }
        Intent intent = getIntent(context, cls, extras, flags);
        context.startActivity(intent);
    }

    public static void jumpForResult(Activity activity, Class<? extends Activity> cls) {
        jumpForResult(activity, cls, null);
    }

    public static void jumpForResult(Activity activity, Class<? extends Activity> cls, Bundle extras) {
        jumpForResult(activity, cls, 0, extras);
    }

    public static void jumpForResult(Activity activity, Class<? extends Activity> cls, int requestCode) {
        jumpForResult(activity, cls, requestCode, null);
    }

    public static void jumpForResult(Activity activity, Class<? extends Activity> cls, int requestCode, Bundle extras) {
        jumpForResult(activity, cls, requestCode, extras, 0);
    }

    public static void jumpForResult(Activity activity, Class<? extends Activity> cls, int requestCode, Bundle extras, int flags) {
        if (activity == null) {
            return;
        }
        Intent intent = getIntent(activity, cls, extras, flags);
        activity.startActivityForResult(intent, requestCode);
    }

    public static Intent getIntent(Context context, Class<? extends Activity> cls) {
        return getIntent(context, cls, null, 0);
    }

    public static Intent getIntent(Context context, Class<? extends Activity> cls, Bundle extras, int flags) {
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
        return intent;
    }

    public static void jump(Context context, String action) {
        Intent intent = new Intent(action);
        try {
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
