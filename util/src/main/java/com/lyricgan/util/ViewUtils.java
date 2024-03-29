package com.lyricgan.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.text.Selection;
import android.text.Spannable;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 视图工具类
 * @author Lyric Gan
 */
public class ViewUtils {
    private static long sLastOperateTime;
    private static final long[] TIME_ARRAY = new long[2];

    private ViewUtils() {
    }

    public static boolean isFastOperated(long maxDelayTimes) {
        long time = System.currentTimeMillis();
        long dis = time - sLastOperateTime;
        if (0 < dis && dis < maxDelayTimes) {
            return true;
        }
        sLastOperateTime = time;
        return false;
    }

    public static boolean isOnDoubleClick(int duration) {
        System.arraycopy(TIME_ARRAY, 1, TIME_ARRAY, 0, TIME_ARRAY.length - 1);
        TIME_ARRAY[TIME_ARRAY.length - 1] = SystemClock.uptimeMillis();
        return TIME_ARRAY[0] >= (SystemClock.uptimeMillis() - duration);
    }

    public static int[] getLocationOnScreen(View view) {
        int[] outLocation = new int[2];
        if (view != null) {
            view.getLocationOnScreen(outLocation);
        }
        return outLocation;
    }

    public static int[] getLocationInWindow(View view) {
        int[] outLocation = new int[2];
        if (view != null) {
            view.getLocationInWindow(outLocation);
        }
        return outLocation;
    }

    /**
     * 设置视图高度
     * @param view View
     * @param height height
     */
    private static void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }

    /**
     * 设置ListView高度，{@link # getListViewHeightBasedOnChildren(ListView)}
     * @param view ListView
     */
    public static void setListViewHeightBasedOnChildren(ListView view) {
        setViewHeight(view, getListViewHeightBasedOnChildren(view));
    }

    /**
     * 获取ListView的高度，通过计算 {@link ListView}
     * @param view ListView
     * @return int
     */
    public static int getListViewHeightBasedOnChildren(ListView view) {
        int height = getAbsListViewHeightBasedOnChildren(view);
        ListAdapter adapter;
        int adapterCount;
        if (view != null && (adapter = view.getAdapter()) != null && (adapterCount = adapter.getCount()) > 0) {
            height += view.getDividerHeight() * (adapterCount - 1);
        }
        return height;
    }

    /**
     * 设置AbsListView的高度，通过计算 {@link # getAbsListViewHeightBasedOnChildren(AbsListView)}
     * @param view AbsListView
     */
    public static void setAbsListViewHeightBasedOnChildren(AbsListView view) {
        setViewHeight(view, getAbsListViewHeightBasedOnChildren(view));
    }

    /**
     * 获取AbsListView高度，{@link AbsListView}
     * @param view AbsListView
     * @return int
     */
    public static int getAbsListViewHeightBasedOnChildren(AbsListView view) {
        ListAdapter adapter;
        if (view == null || (adapter = view.getAdapter()) == null) {
            return 0;
        }
        int height = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, view);
            if (item instanceof ViewGroup) {
                item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            item.measure(0, 0);
            height += item.getMeasuredHeight();
        }
        height += view.getPaddingTop() + view.getPaddingBottom();
        return height;
    }

    /**
     * 设置输入框光标停留在文字后面
     * @param context Context
     * @param editText EditText
     */
    public static void setTextCursor(Context context, EditText editText) {
        Spannable text = editText.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
    }

    /**
     * 解决ScrollView嵌套导致的高度计算问题
     * @return the measure specification based on size and mode
     */
    public static int getExpandSpec() {
        return View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
    }

    /**
     * 获取状态栏高度
     * @param context Context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
            if (statusBarHeight <= 0) {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object obj = clazz.newInstance();
                Field field = clazz.getField("status_bar_height");
                Object heightObject = field.get(obj);
                if (heightObject != null) {
                    int height = Integer.parseInt(heightObject.toString());
                    statusBarHeight = context.getResources().getDimensionPixelSize(height);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static void setStatusBarOverlay(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    public static void hideSystemUI(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public static void showSystemUI(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 获取虚拟按键栏高度
     * @param context Context
     * @return 虚拟按键栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        int height = 0;
        if (isMeizu()) {
            height = getMeizuBarHeight(context);
        } else if (hasNavigationBar(context)) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                height = resources.getDimensionPixelSize(resourceId);
            }
        }
        return height;
    }

    public static boolean hasNavigationBar(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean flag = resources.getBoolean(resourceId);
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                flag = false;
            } else if ("0".equals(sNavBarOverride)) {
                flag = true;
            }
            return flag;
        } else {
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return sNavBarOverride;
    }

    private static boolean isMeizu() {
        return Build.BRAND.equals("Meizu");
    }

    private static int getMeizuBarHeight(Context context) {
        try {
            Class cls = Class.forName("com.android.internal.R$dimen");
            Object obj = cls.newInstance();
            Field field = cls.getField("mz_action_button_min_height");
            Object heightObject = field.get(obj);
            if (heightObject != null) {
                int height = Integer.parseInt(heightObject.toString());
                return context.getResources().getDimensionPixelSize(height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 执行视图触摸反馈震动效果
     * @param view 视图
     * @return true or false
     */
    public static boolean performHapticFeedback(View view) {
        return view != null && (view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING));
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} to a fullscreen opaque
     * Activity.
     * <p>
     * Call this whenever the background of a translucent Activity has changed
     * to become opaque. Doing so will allow the {@link android.view.Surface} of
     * the Activity behind to be released.
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} back from opaque to
     * translucent following a call to
     * {@link #convertActivityFromTranslucent(Activity)} .
     * <p>
     * Calling this allows the Activity behind this one to be seen again. Once
     * all such Activities have been redrawn
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityToTranslucent(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerCls = null;
            for (Class<?> cls : classes) {
                if (cls.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerCls = cls;
                }
            }
            if (Build.VERSION.SDK_INT >= 21) {
                Method method = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerCls, ActivityOptions.class);
                method.setAccessible(true);
                method.invoke(activity, new Object[] { null, null });
            } else {
                Method method = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerCls);
                method.setAccessible(true);
                method.invoke(activity, new Object[] { null });
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 获取背景Drawable，用于设置背景
     * @param color 填充颜色
     * @param cornerRadius 圆角半径
     * @param strokeWidth 圆边宽度
     * @param strokeColor 圆边颜色
     * @return 背景Drawable
     */
    public static GradientDrawable getDrawable(int color, int cornerRadius, int strokeWidth, int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setColor(color);
        drawable.setCornerRadius(cornerRadius);
        drawable.setStroke(strokeWidth, strokeColor);
        return drawable;
    }

    /**
     * 获取背景Drawable，区分默认和按下
     * @param normalDrawable 默认背景
     * @param pressedDrawable 按下背景
     * @return 背景Drawable，区分默认和按下
     */
    public static StateListDrawable getSelector(Drawable normalDrawable, Drawable pressedDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressedDrawable);
        drawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        drawable.addState(new int[]{}, normalDrawable);
        return drawable;
    }
}
