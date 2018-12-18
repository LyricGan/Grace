package com.lyric.arch;

import android.view.View;

/**
 * base title bar
 *
 * @author lyricgan
 */
public class BaseTitleBar implements ITitleBar {
    private View mTitleView;

    public BaseTitleBar(View titleView) {
        this.mTitleView = titleView;
    }

    public View getTitleView() {
        return mTitleView;
    }

    public void setTitleView(View titleView) {
        this.mTitleView = titleView;
    }
}
