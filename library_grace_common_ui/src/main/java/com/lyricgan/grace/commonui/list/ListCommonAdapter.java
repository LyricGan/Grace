package com.lyricgan.grace.commonui.list;

import android.content.Context;

import java.util.List;

public abstract class ListCommonAdapter<T> extends ListTypeAdapter<T> {

    public ListCommonAdapter(Context context, List<T> items) {
        super(context, items);
        addAdapterItemView(new ListAdapterItemView<T>() {
            @Override
            public int getItemViewLayoutId() {
                return ListCommonAdapter.this.getItemViewLayoutId();
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

    protected abstract int getItemViewLayoutId();

    protected abstract void convert(ListViewHolder viewHolder, T item, int position);
}
