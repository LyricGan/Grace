package com.lyricgan.grace.samples.app;

import android.os.Bundle;
import android.view.View;

import com.lyricgan.base.GraceActivity;
import com.lyricgan.grace.samples.R;
import com.lyricgan.grace.samples.util.ActivityStackManager;
import com.lyricgan.grace.samples.widget.TitleBar;

public abstract class BaseActivity extends GraceActivity implements View.OnClickListener {
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
        if (mTitleBar == null) {
            mTitleBar = new TitleBar(decorView);
        }
        onCreateTitleBar(mTitleBar, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_text || v.getId() == R.id.title_bar_left_image) {
            onBackPressed();
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

    protected void onCreateTitleBar(TitleBar titleBar, Bundle savedInstanceState) {
        titleBar.setLeftTextOnClickListener(this);
        titleBar.setLeftImageOnClickListener(this);
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }
}
