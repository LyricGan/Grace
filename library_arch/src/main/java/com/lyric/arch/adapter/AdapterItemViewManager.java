package com.lyric.arch.adapter;

import android.support.v4.util.SparseArrayCompat;

import com.lyric.arch.adapter.list.ListViewHolder;
import com.lyric.arch.adapter.recycler.RecyclerViewHolder;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public class AdapterItemViewManager<T> {
    private SparseArrayCompat<AdapterItemView<T>> mAdapterItemViewArray = new SparseArrayCompat<>();

    public AdapterItemViewManager<T> addAdapterItemView(AdapterItemView<T> adapterItemView) {
        int viewType = mAdapterItemViewArray.size();
        if (adapterItemView != null) {
            mAdapterItemViewArray.put(viewType, adapterItemView);
        }
        return this;
    }

    public AdapterItemViewManager<T> addAdapterItemView(int viewType, AdapterItemView<T> adapterItemView) {
        if (mAdapterItemViewArray.get(viewType) == null) {
            mAdapterItemViewArray.put(viewType, adapterItemView);
        }
        return this;
    }

    public AdapterItemViewManager<T> removeAdapterItemView(AdapterItemView<T> adapterItemView) {
        if (adapterItemView != null) {
            int indexToRemove = mAdapterItemViewArray.indexOfValue(adapterItemView);
            if (indexToRemove >= 0) {
                mAdapterItemViewArray.removeAt(indexToRemove);
            }
        }
        return this;
    }

    public AdapterItemViewManager<T> removeAdapterItemView(int itemType) {
        int indexToRemove = mAdapterItemViewArray.indexOfKey(itemType);
        if (indexToRemove >= 0) {
            mAdapterItemViewArray.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemViewCount() {
        return mAdapterItemViewArray.size();
    }

    public int getItemViewType(T item, int position) {
        int count = mAdapterItemViewArray.size();
        for (int i = count - 1; i >= 0; i--) {
            AdapterItemView<T> adapterItemView = mAdapterItemViewArray.valueAt(i);
            if (adapterItemView.isForViewType(item, position)) {
                return mAdapterItemViewArray.keyAt(i);
            }
        }
        return 0;
    }

    public void convert(RecyclerViewHolder holder, T item, int position) {
        int count = mAdapterItemViewArray.size();
        for (int i = 0; i < count; i++) {
            AdapterItemView<T> adapterItemView = mAdapterItemViewArray.valueAt(i);
            if (adapterItemView.isForViewType(item, position)) {
                adapterItemView.convert(holder, item, position);
                return;
            }
        }
    }

    public void convert(ListViewHolder holder, T item, int position) {
        int count = mAdapterItemViewArray.size();
        for (int i = 0; i < count; i++) {
            AdapterItemView<T> delegate = mAdapterItemViewArray.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
    }

    public AdapterItemView<T> getAdapterItemView(T item, int position) {
        int count = mAdapterItemViewArray.size();
        for (int i = count - 1; i >= 0; i--) {
            AdapterItemView<T> delegate = mAdapterItemViewArray.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegate;
            }
        }
        return null;
    }

    public AdapterItemView<T> getAdapterItemView(int viewType) {
        return mAdapterItemViewArray.get(viewType);
    }

    public int getItemViewLayoutId(int viewType) {
        return getAdapterItemView(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(AdapterItemView<T> adapterItemView) {
        return mAdapterItemViewArray.indexOfValue(adapterItemView);
    }
}
