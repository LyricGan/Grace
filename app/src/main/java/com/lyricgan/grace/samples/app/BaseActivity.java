package com.lyricgan.grace.samples.app;

import android.os.Bundle;
import android.view.View;

import com.lyricgan.arch.GraceActivity;
import com.lyricgan.grace.samples.util.ActivityStackManager;
import com.lyricgan.grace.samples.widget.TitleBar;

public abstract class BaseActivity extends GraceActivity {
    private TitleBar mTitleBar;

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
        ActivityStackManager.getInstance().add(this);
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, Bundle args) {
    }

    @Override
    protected void onCreateTitleBar(View decorView, Bundle savedInstanceState) {
        TitleBar titleBar = mTitleBar;
        if (titleBar == null) {
            titleBar = new TitleBar(decorView);
            titleBar.bindViews();
            titleBar.setLeftTextOnClickListener(v -> onBackPressed());
            titleBar.setLeftImageOnClickListener(v -> onBackPressed());
            mTitleBar = titleBar;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().remove(this);
    }

    @Override
    public void showLoading(CharSequence message, boolean cancelable) {
    }

    @Override
    public void hideLoading() {
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }
}
