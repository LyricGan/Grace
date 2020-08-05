package com.lyricgan.grace.widget.list;

public interface ListAdapterItemView<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ListViewHolder holder, T item, int position);
}
