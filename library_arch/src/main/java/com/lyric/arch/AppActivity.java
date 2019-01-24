package com.lyric.arch;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.lyric.utils.AbstractHandler;
import com.lyric.utils.AppViewUtils;
import com.lyric.utils.LogUtils;
import com.lyric.utils.ToastUtils;

/**
 * base activity
 *
 * @author lyricgan
 */
public abstract class AppActivity extends AppCompatActivity implements AppListener, View.OnClickListener {
    private Handler mHandler;
    private AppTitleBar titleBar;

    private static class InnerHandler extends AbstractHandler<AppListener> {

        InnerHandler(AppListener object) {
            super(object);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AppListener listener = get();
            if (listener != null) {
                listener.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreatePrepare(savedInstanceState);
        super.onCreate(savedInstanceState);
        logMessage("onCreate()");
        ActivityStackManager.getInstance().add(this);
        mHandler = new InnerHandler(this);

        setContentView(getContentViewId());
        View decorView = getWindow().getDecorView();
        View titleView = decorView.findViewById(R.id.title_bar);
        if (titleBar == null) {
            titleBar = new AppTitleBar(titleView);
            titleBar.setOnClickListener(this);
        }
        onCreateTitleBar(titleBar);

        onCreateContentView(decorView, savedInstanceState, getIntent().getExtras());

        onCreateData(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logMessage("onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logMessage("onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logMessage("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logMessage("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logMessage("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logMessage("onDestroy()");
        ActivityStackManager.getInstance().remove(this);
    }

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    protected void onCreateTitleBar(AppTitleBar titleBar) {
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.title_bar_left_text || viewId == R.id.title_bar_left_image) {
            onBackPressed();
        }
    }

    @Override
    public void showLoading(CharSequence message) {
        showLoading("", true);
    }

    @Override
    public void showLoading(CharSequence message, boolean cancelable) {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void handleMessage(Message msg) {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoHideKeyboard()) {
            if (MotionEvent.ACTION_DOWN == ev.getAction()) {
                View v = getCurrentFocus();
                if (AppViewUtils.isShouldHideKeyboard(v, ev)) {
                    AppViewUtils.hideSoftKeyboard(this, v.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean isAutoHideKeyboard() {
        return false;
    }

    public boolean isDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return isDestroyed();
        }
        return isFinishing();
    }

    public void toast(int resId) {
        ToastUtils.show(this, resId);
    }

    public void toast(CharSequence text) {
        ToastUtils.show(this, text);
    }

    private void logMessage(String message) {
        LogUtils.d(getClass().getName(), message);
    }
}