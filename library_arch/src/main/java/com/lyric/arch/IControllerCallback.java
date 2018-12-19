package com.lyric.arch;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author lyricgan
 */
public interface IControllerCallback<E> {

    void onCreateView(View view, ViewGroup parent);

    void onUpdateView(E data);

    void onDestroyView();
}
