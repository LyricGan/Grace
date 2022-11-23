package com.lyricgan.grace.samples.app;

import android.os.Bundle;
import android.view.View;

import com.lyricgan.arch.GraceActivity;

public abstract class BaseActivity extends GraceActivity {
    private TitleBar mTitleBar;
    private final View.OnClickListener mBackPressedListener = v -> onBackPressed();

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, Bundle args) {
    }

    @Override
    protected void onCreateTitleBar(View decorView, Bundle savedInstanceState) {
        TitleBar titleBar = mTitleBar;
        if (titleBar == null) {
            titleBar = new TitleBar(decorView);
            titleBar.setLeftTextOnClickListener(mBackPressedListener);
            titleBar.setLeftImageOnClickListener(mBackPressedListener);
            mTitleBar = titleBar;
        }
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
