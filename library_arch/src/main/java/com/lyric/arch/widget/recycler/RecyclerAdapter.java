package com.lyric.arch.widget.recycler;

import android.content.Context;
import android.support.annotation.NonNull;

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

            @Override
            public void convert(RecyclerViewHolder holder, T item, int position, @NonNull List<Object> payloads) {
                RecyclerAdapter.this.convert(holder, item, position, payloads);
            }
        });
    }

    protected abstract void convert(RecyclerViewHolder holder, T item, int position);

    protected void convert(RecyclerViewHolder holder, T item, int position, @NonNull List<Object> payloads) {
    }
}
