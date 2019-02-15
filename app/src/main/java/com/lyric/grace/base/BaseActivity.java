package com.lyric.grace.base;

import com.lyric.arch.AppActivity;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public abstract class BaseActivity extends AppActivity {

    @Override
    public void showLoading(CharSequence message, boolean cancelable) {
        super.showLoading(message, cancelable);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }
}
