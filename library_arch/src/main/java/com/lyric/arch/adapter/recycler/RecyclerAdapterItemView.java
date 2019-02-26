package com.lyric.arch.adapter.recycler;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public interface RecyclerAdapterItemView<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RecyclerViewHolder holder, T item, int position);
}
