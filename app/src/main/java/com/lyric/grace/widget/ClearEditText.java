package com.lyric.grace.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;

/**
 * @author lyricgan
 * @description 带清除功能的EditText
 * @time 2016/1/29 10:58
 */
public class ClearEditText extends AutoCompleteTextView implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    private boolean mHasFocus;
    private OnEditTextFocusChangeListener mEditTextFocusChangeListener;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            // 设置默认图片
            return;
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setCursorVisible(true);
        // 默认设置隐藏图标
        setClearIconVisible(false);

        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()) {
            float x = event.getX();
            int width = getWidth();
            if (getCompoundDrawables()[2] != null) {
                boolean touch = x > (width - getTotalPaddingRight()) && (x < (width - getPaddingRight()));
                if (touch) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.mHasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (mEditTextFocusChangeListener != null) {
            mEditTextFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    /**
     * 设置清除图标的显示与隐藏
     * @param visible true or false
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelative(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (mHasFocus) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public void setOnEditTextFocusChangeListener(OnEditTextFocusChangeListener listener) {
        this.mEditTextFocusChangeListener = listener;
    }

    /**
     * 设置默认晃动动画
     */
    public void setDefaultAnimation() {
        this.setAnimation(shakeAnimation(5, 1000));
    }

    /**
     * 左右晃动动画
     * @param counts 1秒钟晃动多少下
     * @return Animation
     */
    public static Animation shakeAnimation(int counts, long durationMillis) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(durationMillis);
        return translateAnimation;
    }

    /**
     * 可删除按钮的焦点发生变化的回调
     */
    public interface OnEditTextFocusChangeListener {

        void onFocusChange(View v, boolean hasFocus);
    }
}
