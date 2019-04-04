package com.lyric.support.widget.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public abstract class RecyclerAdapter<T> extends RecyclerTypeAdapter<T> {

    public RecyclerAdapter(final Context context, List<T> items) {
        super(context, items);
        addAdapterItemView(new RecyclerAdapterItemView<T>() {
            @Override
            public int getItemViewLayoutId() {
                return getItemLayoutId();
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(RecyclerViewHolder holder, T item, int position) {
                convertItemView(holder, item, position);
            }
        });
    }

    protected abstract @LayoutRes
    int getItemLayoutId();

    protected abstract void convertItemView(RecyclerViewHolder holder, T item, int position);
}
