package com.lyric.arch.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public interface RecyclerAdapterItemView<T> {

    @LayoutRes int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RecyclerViewHolder holder, T item, int position);

    void convert(RecyclerViewHolder holder, T item, int position, @NonNull List<Object> payloads);
}
