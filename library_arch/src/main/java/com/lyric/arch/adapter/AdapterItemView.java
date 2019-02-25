package com.lyric.arch.adapter;

import android.support.annotation.LayoutRes;

import com.lyric.arch.adapter.list.ListViewHolder;
import com.lyric.arch.adapter.recycler.RecyclerViewHolder;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public interface AdapterItemView<T> {

    @LayoutRes
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RecyclerViewHolder holder, T item, int position);

    void convert(ListViewHolder holder, T item, int position);
}
