package com.lyric.arch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 基础接口类
 *
 * @author lyricgan
 */
public interface IBaseListener {

    void onCreatePrepare(Bundle savedInstanceState);

    @LayoutRes int getContentViewId();

    void onCreateContentView(View view, Bundle savedInstanceState, Bundle args, AppTitleBar titleBar);

    void onCreateData(Bundle savedInstanceState);

    Handler getHandler();

    void handleMessage(Message msg);

    void showLoading(CharSequence message);

    void showLoading(CharSequence message, boolean cancelable);

    void hideLoading();
}