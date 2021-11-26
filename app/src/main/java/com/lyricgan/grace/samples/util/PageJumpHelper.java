package com.lyricgan.grace.samples.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lyricgan.grace.samples.constants.IExtras;

/**
 * 页面跳转工具类，将不同的页面跳转统一封装，方便维护
 *
 * @author Lyric Gan
 */
public class PageJumpHelper {

    private PageJumpHelper() {
    }

    /**
     * 跳转到Fragment页面
     * @param context Context
     * @param fragmentClass Fragment
     * @param activityClass Activity
     * @param title 页面标题
     * @param params 页面参数
     */
    public static void jumpFragmentPage(Context context, @NonNull Class<? extends Fragment> fragmentClass, @NonNull Class<? extends Activity> activityClass, String title, Bundle params) {
        jumpFragmentPage(context, fragmentClass, activityClass, title, params, 0);
    }

    /**
     * 跳转到Fragment页面
     * @param context Context
     * @param fragmentClass Fragment
     * @param activityClass Activity
     * @param title 页面标题
     * @param params 页面参数
     * @param flags 跳转标志位
     */
    public static void jumpFragmentPage(Context context, @NonNull Class<? extends Fragment> fragmentClass, @NonNull Class<? extends Activity> activityClass, String title, Bundle params, int flags) {
        Bundle extras = getFragmentPageParams(fragmentClass, title, params);
        jumpActivity(context, activityClass, extras, flags);
    }

    public static Intent getFragmentPageIntent(Context context, @NonNull Class<? extends Fragment> fragmentClass, @NonNull Class<? extends Activity> activityClass, String title, Bundle params) {
        return getFragmentPageIntent(context, fragmentClass, activityClass, title, params, 0);
    }

    public static Intent getFragmentPageIntent(Context context, @NonNull Class<? extends Fragment> fragmentClass, @NonNull Class<? extends Activity> activityClass, String title, Bundle params, int flags) {
        Bundle extras = getFragmentPageParams(fragmentClass, title, params);
        return getJumpIntent(context, activityClass, extras, flags);
    }

    private static Bundle getFragmentPageParams(@NonNull Class<? extends Fragment> fragmentClass, String title, Bundle params) {
        Bundle extras = new Bundle();
        extras.putString(IExtras.KEY_NAME, fragmentClass.getName());
        extras.putString(IExtras.KEY_TITLE, title);
        extras.putBundle(IExtras.KEY_PARAMS, params);
        return extras;
    }

    public static void jumpActivity(Context context, Class<? extends Activity> cls) {
        jumpActivity(context, cls, null);
    }

    public static void jumpActivity(Context context, Class<? extends Activity> cls, Bundle extras) {
        jumpActivity(context, cls, extras, 0);
    }

    public static void jumpActivity(Context context, Class<? extends Activity> cls, Bundle extras, int flags) {
        if (context == null) {
            return;
        }
        Intent intent = getJumpIntent(context, cls, extras, flags);
        context.startActivity(intent);
    }

    public static void jumpActivityForResult(Activity activity, Class<? extends Activity> cls) {
        jumpActivityForResult(activity, cls, null);
    }

    public static void jumpActivityForResult(Activity activity, Class<? extends Activity> cls, Bundle extras) {
        jumpActivityForResult(activity, cls, 0, extras);
    }

    public static void jumpActivityForResult(Activity activity, Class<? extends Activity> cls, int requestCode) {
        jumpActivityForResult(activity, cls, requestCode, null);
    }

    public static void jumpActivityForResult(Activity activity, Class<? extends Activity> cls, int requestCode, Bundle extras) {
        jumpActivityForResult(activity, cls, requestCode, extras, 0);
    }

    public static void jumpActivityForResult(Activity activity, Class<? extends Activity> cls, int requestCode, Bundle extras, int flags) {
        if (activity == null) {
            return;
        }
        Intent intent = getJumpIntent(activity, cls, extras, flags);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void jumpActivityForResult(Fragment fragment, Class<? extends Activity> cls) {
        jumpActivityForResult(fragment, cls, null);
    }

    public static void jumpActivityForResult(Fragment fragment, Class<? extends Activity> cls, Bundle extras) {
        jumpActivityForResult(fragment, cls, 0, extras);
    }

    public static void jumpActivityForResult(Fragment fragment, Class<? extends Activity> cls, int requestCode) {
        jumpActivityForResult(fragment, cls, requestCode, null);
    }

    public static void jumpActivityForResult(Fragment fragment, Class<? extends Activity> cls, int requestCode, Bundle extras) {
        jumpActivityForResult(fragment, cls, requestCode, extras, 0);
    }

    public static void jumpActivityForResult(Fragment fragment, Class<? extends Activity> cls, int requestCode, Bundle extras, int flags) {
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

    public static Intent getJumpIntent(Context context, Class<? extends Activity> cls) {
        return getJumpIntent(context, cls, null, 0);
    }

    public static Intent getJumpIntent(Context context, Class<? extends Activity> cls, Bundle extras, int flags) {
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

    public static void jumpIntent(Context context, String action) {
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

    public static void jumpIntentForResult(Activity activity, String action, int requestCode) {
        Intent intent = new Intent(action);
        try {
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
