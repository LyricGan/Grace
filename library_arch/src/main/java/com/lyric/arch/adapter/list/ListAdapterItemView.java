package com.lyric.arch.adapter.list;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public interface ListAdapterItemView<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ListViewHolder holder, T item, int position);
}
