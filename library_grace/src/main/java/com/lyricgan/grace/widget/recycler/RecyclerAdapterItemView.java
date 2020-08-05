package com.lyricgan.grace.widget.recycler;

import androidx.annotation.LayoutRes;

public interface RecyclerAdapterItemView<T> {

    @LayoutRes
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RecyclerViewHolder holder, T item, int position);
}
