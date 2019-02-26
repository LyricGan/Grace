package com.lyric.arch.adapter.recycler;

import android.support.v4.util.SparseArrayCompat;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public class RecyclerAdapterItemViewManager<T> {
    private SparseArrayCompat<RecyclerAdapterItemView<T>> mAdapterItemViewArray = new SparseArrayCompat<>();

    public RecyclerAdapterItemViewManager<T> addAdapterItemView(RecyclerAdapterItemView<T> adapterItemView) {
        int viewType = mAdapterItemViewArray.size();
        if (adapterItemView != null) {
            mAdapterItemViewArray.put(viewType, adapterItemView);
        }
        return this;
    }

    public RecyclerAdapterItemViewManager<T> addAdapterItemView(int viewType, RecyclerAdapterItemView<T> adapterItemView) {
        if (mAdapterItemViewArray.get(viewType) == null) {
            mAdapterItemViewArray.put(viewType, adapterItemView);
        }
        return this;
    }

    public RecyclerAdapterItemViewManager<T> removeAdapterItemView(RecyclerAdapterItemView<T> adapterItemView) {
        if (adapterItemView != null) {
            int indexToRemove = mAdapterItemViewArray.indexOfValue(adapterItemView);
            if (indexToRemove >= 0) {
                mAdapterItemViewArray.removeAt(indexToRemove);
            }
        }
        return this;
    }

    public RecyclerAdapterItemViewManager<T> removeAdapterItemView(int itemType) {
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
            RecyclerAdapterItemView<T> adapterItemView = mAdapterItemViewArray.valueAt(i);
            if (adapterItemView.isForViewType(item, position)) {
                return mAdapterItemViewArray.keyAt(i);
            }
        }
        return 0;
    }

    public void convert(RecyclerViewHolder holder, T item, int position) {
        int count = mAdapterItemViewArray.size();
        for (int i = 0; i < count; i++) {
            RecyclerAdapterItemView<T> adapterItemView = mAdapterItemViewArray.valueAt(i);
            if (adapterItemView.isForViewType(item, position)) {
                adapterItemView.convert(holder, item, position);
                return;
            }
        }
    }

    public RecyclerAdapterItemView<T> getAdapterItemView(T item, int position) {
        int count = mAdapterItemViewArray.size();
        for (int i = count - 1; i >= 0; i--) {
            RecyclerAdapterItemView<T> adapterItemView = mAdapterItemViewArray.valueAt(i);
            if (adapterItemView.isForViewType(item, position)) {
                return adapterItemView;
            }
        }
        return null;
    }

    public RecyclerAdapterItemView<T> getAdapterItemView(int viewType) {
        return mAdapterItemViewArray.get(viewType);
    }

    public int getItemViewLayoutId(int viewType) {
        return getAdapterItemView(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(RecyclerAdapterItemView<T> adapterItemView) {
        return mAdapterItemViewArray.indexOfValue(adapterItemView);
    }
}
