package com.lyric.arch;

import android.view.View;

/**
 * @author lyricgan
 */
public interface IControllerCallback<E> {

    void onCreateView(View view);

    void onUpdateView(E data);

    void onDestroyView();
}
