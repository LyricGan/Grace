package com.lyric.arch.adapter.recycler;

import android.content.Context;

import java.util.List;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public abstract class RecyclerAdapter<T> extends RecyclerTypeAdapter<T> {

    public RecyclerAdapter(final Context context, List<T> items, final int layoutId) {
        super(context, items);
        addAdapterItemView(new RecyclerAdapterItemView<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(RecyclerViewHolder holder, T item, int position) {
                RecyclerAdapter.this.convert(holder, item, position);
            }
        });
    }

    protected abstract void convert(RecyclerViewHolder holder, T item, int position);
}
