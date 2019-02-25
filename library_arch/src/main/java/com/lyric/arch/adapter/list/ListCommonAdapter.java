package com.lyric.arch.adapter.list;

import android.content.Context;

import com.lyric.arch.adapter.AdapterItemView;
import com.lyric.arch.adapter.recycler.RecyclerViewHolder;

import java.util.List;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public abstract class ListCommonAdapter<T> extends ListTypeAdapter<T> {

    public ListCommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        addAdapterItemView(new AdapterItemView<T>() {
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
            }

            @Override
            public void convert(ListViewHolder holder, T t, int position) {
                ListCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ListViewHolder viewHolder, T item, int position);
}
