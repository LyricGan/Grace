package com.lyricgan.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;

public interface GraceAppListener {

    void onCreatePrepare(Bundle savedInstanceState);

    void onCreateExtras(Bundle savedInstanceState, Bundle args);

    @LayoutRes int getContentViewId();

    void onCreateContentView(View view, Bundle savedInstanceState);

    void onCreateData(Bundle savedInstanceState);

    void showLoading(CharSequence message, boolean cancelable);

    void hideLoading();
}