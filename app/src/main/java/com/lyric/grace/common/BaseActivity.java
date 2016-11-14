package com.lyric.grace.common;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lyric.grace.Grace;
import com.lyric.grace.R;
import com.lyric.grace.utils.BuildVersionUtils;
import com.lyric.grace.utils.ViewUtils;
import com.lyric.grace.widget.dialog.LoadingDialog;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener, IBaseListener {
    private boolean mDestroy = false;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onPrepareCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        onViewCreate(savedInstanceState);
        if (isInject()) {
            injectStatusBar();
        }
    }

    @Override
    public abstract void onViewCreate(Bundle savedInstanceState);

    @Override
    public void onPrepareCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onViewClick(View v) {
    }

    @Override
    public void onClick(View v) {
        onViewClick(v);
    }

    @Override
    protected void onResume() {
        mDestroy = false;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mDestroy = true;
        super.onDestroy();
    }

    protected boolean isDestroy() {
        if (BuildVersionUtils.hasJellyBean()) {
            return isDestroyed();
        }
        return mDestroy;
    }

    protected void showLoadingDialog() {
        showLoadingDialog("");
    }

    protected void showLoadingDialog(CharSequence message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.setMessage(message);
        mLoadingDialog.show();
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    protected boolean isInject() {
        return false;
    }

    protected void injectStatusBar() {
        ViewUtils.setStatusBarColor(this, ContextCompat.getColor(Grace.getContext(), R.color.color_title_bar_bg));
    }

    protected boolean isHideKeyboard() {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (MotionEvent.ACTION_DOWN == ev.getAction()) {
            View v = getCurrentFocus();
            if (isHideKeyboard() && isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] location = {0, 0};
            v.getLocationInWindow(location);
            int left = location[0];
            int top = location[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token == null) {
            return;
        }
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}