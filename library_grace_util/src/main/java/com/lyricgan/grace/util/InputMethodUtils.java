package com.lyricgan.grace.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 输入法工具类
 * @author Lyric Gan
 * @since 2019-12-07
 */
public class InputMethodUtils {

    private static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static void showSoftInput(View view) {
        InputMethodManager imm = getInputMethodManager(view.getContext());
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void showSoftInput(View view, int flags) {
        InputMethodManager imm = getInputMethodManager(view.getContext());
        if (imm != null) {
            imm.showSoftInput(view, flags);
        }
    }

    public static void hideSoftInput(View view) {
        hideSoftInput(view.getContext(), view.getWindowToken(), 0);
    }

    public static void hideSoftInput(Context context, IBinder windowToken, int flags) {
        InputMethodManager imm = getInputMethodManager(context);
        if (imm != null) {
            imm.hideSoftInputFromWindow(windowToken, flags);
        }
    }

    /**
     * 隐藏软键盘
     * @param context Context
     * @param token EditText
     */
    public static void hideSoftKeyboard(Context context, IBinder token) {
        InputMethodManager imm = null;
        try {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软键盘
     * @param context Context
     * @param editText EditText
     */
    public static void showSoftKeyboard(Context context, EditText editText) {
        InputMethodManager imm = null;
        try {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (imm != null) {
            boolean isInputOpen = imm.isActive();
            if (!isInputOpen) {
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    public static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] location = {0, 0};
            v.getLocationInWindow(location);
            int left = location[0];
            int top = location[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            float eventX = event.getX();
            float eventY = event.getY();
            return !(eventX > left && eventX < right && eventY > top && eventY < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    public static void toggle(Context context) {
        InputMethodManager imm = getInputMethodManager(context);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isActive(Context context) {
        InputMethodManager imm = getInputMethodManager(context);
        return imm != null && imm.isActive();
    }

    /**
     * 监听软键盘弹出和隐藏
     * @param activity Activity
     * @param listener 软键盘弹出和隐藏监听事件
     */
    public static void observeSoftKeyboard(Activity activity, final OnSoftKeyboardChangedListener listener) {
        if (activity == null) {
            return;
        }
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            int previousKeyboardHeight = -1;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                int height = decorView.getHeight();
                int keyboardHeight = height - displayHeight;
                if (previousKeyboardHeight != keyboardHeight) {
                    boolean visible = (double) displayHeight / height <= 0.8;
                    listener.onSoftKeyBoardChange(keyboardHeight, visible);
                }
                previousKeyboardHeight = keyboardHeight;
            }
        });
    }

    /**
     * 软键盘变化监听事件
     */
    public interface OnSoftKeyboardChangedListener {

        void onSoftKeyBoardChange(int height, boolean visible);
    }
}
