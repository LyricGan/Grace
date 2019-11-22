package com.lyric.support.widget.recycler;

import android.support.annotation.LayoutRes;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public interface RecyclerAdapterItemView<T> {

    @LayoutRes
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RecyclerViewHolder holder, T item, int position);
}