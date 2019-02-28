package com.lyric.arch.widget;

import android.content.Context;

import java.util.List;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public abstract class ListCommonAdapter<T> extends ListTypeAdapter<T> {

    public ListCommonAdapter(Context context, List<T> items, final int layoutId) {
        super(context, items);
        addAdapterItemView(new ListAdapterItemView<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ListViewHolder holder, T item, int position) {
                ListCommonAdapter.this.convert(holder, item, position);
            }
        });
    }

    protected abstract void convert(ListViewHolder viewHolder, T item, int position);
}
