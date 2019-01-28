package com.lyric.arch;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 基础接口类
 *
 * @author lyricgan
 */
public interface AppListener {

    void onCreatePrepare(Bundle savedInstanceState, Bundle args);

    @LayoutRes int getContentViewId();

    void onCreateContentView(View view, Bundle savedInstanceState, Bundle args);

    void onCreateData(Bundle savedInstanceState, Bundle args);

    void showLoading(CharSequence message, boolean cancelable);

    void hideLoading();
}