package com.lyric.support.app;

import android.view.View;
import android.view.ViewGroup;

/**
 * 视图控制器接口
 * @author lyricgan
 */
public interface AppControllerListener<E> {

    void onCreateView(View view, ViewGroup parent);

    void onUpdateView(E data);

    void onDestroyView();
}
