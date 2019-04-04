package com.lyric.grace.base;

import android.os.Bundle;
import android.view.View;

import com.lyric.support.app.AppActivity;
import com.lyric.support.app.AppTitleBar;
import com.lyric.grace.R;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public abstract class BaseActivity extends AppActivity {

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, Bundle args) {
    }

    @Override
    protected void onCreateTitleBar(AppTitleBar titleBar, Bundle savedInstanceState) {
        titleBar.setLeftTextOnClickListener(this);
        titleBar.setLeftImageOnClickListener(this);
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
}
