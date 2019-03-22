package com.lyric.arch.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * base activity
 *
 * @author lyricgan
 */
public abstract class AppActivity extends AppCompatActivity implements AppListener, View.OnClickListener {
    private AppTitleBar titleBar;
    private boolean mActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreatePrepare(savedInstanceState);
        super.onCreate(savedInstanceState);
        logMessage("onCreate()");
        ActivityStackManager.getInstance().add(this);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            onCreateExtras(savedInstanceState, args);
        }
        setContentView(getContentViewId());
        View decorView = getWindow().getDecorView();
        if (titleBar == null) {
            titleBar = new AppTitleBar(decorView);
        }
        onCreateTitleBar(titleBar, savedInstanceState);

        onCreateContentView(decorView, savedInstanceState);

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
        mActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        logMessage("onPause()");
        mActive = false;
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

    protected abstract void onCreateTitleBar(AppTitleBar titleBar, Bundle savedInstanceState);

    public boolean isDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return isDestroyed();
        }
        return isFinishing();
    }

    /**
     * 返回页面是否处于活动状态
     * @return true or false
     */
    public boolean isActive() {
        return mActive;
    }

    private void logMessage(String message) {
        Log.d(getClass().getName(), message);
    }
}