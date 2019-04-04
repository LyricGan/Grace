package com.lyric.support.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 基础接口类
 *
 * @author lyricgan
 */
public interface AppListener {

    void onCreatePrepare(Bundle savedInstanceState);

    void onCreateExtras(Bundle savedInstanceState, Bundle args);

    @LayoutRes int getContentViewId();

    void onCreateContentView(View view, Bundle savedInstanceState);

    void onCreateData(Bundle savedInstanceState);

    void showLoading(CharSequence message, boolean cancelable);

    void hideLoading();
}