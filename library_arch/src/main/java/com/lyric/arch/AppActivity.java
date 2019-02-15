package com.lyric.arch;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, @NonNull Bundle args) {
    }

    protected void onCreateTitleBar(AppTitleBar titleBar, Bundle savedInstanceState) {
        titleBar.setLeftTextOnClickListener(this);
        titleBar.setLeftImageOnClickListener(this);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_text || v.getId() == R.id.title_bar_left_image) {
            onBackPressed();
        }
    }

    @Override
    public void showLoading(CharSequence message, boolean cancelable) {
    }

    @Override
    public void hideLoading() {
    }

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