package com.lyricgan.grace.widget.list;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class ListTypeAdapter<T> extends BaseAdapter {
    private final Context mContext;
    private List<T> mItems;
    private final AdapterItemViewManager<T> mAdapterItemViewManager;

    public ListTypeAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.mItems = items;
        this.mAdapterItemViewManager = new AdapterItemViewManager<>();

        addAdapterItemViews();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T item = getItem(position);
        AdapterItemView<T> adapterItemView = mAdapterItemViewManager.getAdapterItemView(item, position);
        if (adapterItemView == null) {
            return getEmptyAdapterItemView();
        }
        ListViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ListViewHolder(mContext, parent, adapterItemView.getItemLayoutId());
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ListViewHolder) convertView.getTag();
        }
        convert(viewHolder, item, position);
        return viewHolder.getConvertView();
    }

    @Override
    public int getItemViewType(int position) {
        if (isUseAdapterItemViewManager()) {
            return mAdapterItemViewManager.getItemViewType(getItem(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        if (isUseAdapterItemViewManager()) {
            return mAdapterItemViewManager.getItemViewCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public T getItem(int position) {
        if (isPositionInvalid(position)) {
            return null;
        }
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isUseAdapterItemViewManager() {
        return mAdapterItemViewManager.getItemViewCount() > 0;
    }

    protected void onViewHolderCreated(ListViewHolder holder, View itemView) {
    }

    protected void convert(ListViewHolder viewHolder, T item, int position) {
        mAdapterItemViewManager.convert(viewHolder, item, position);
    }

    protected View getEmptyAdapterItemView() {
        return null;
    }

    protected abstract void addAdapterItemViews();

    public ListTypeAdapter<T> addAdapterItemView(AdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.addAdapterItemView(adapterItemView);
        return this;
    }

    public ListTypeAdapter<T> addAdapterItemView(int viewType, AdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.addAdapterItemView(viewType, adapterItemView);
        return this;
    }

    public ListTypeAdapter<T> removeAdapterItemView(AdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.removeAdapterItemView(adapterItemView);
        return this;
    }

    public ListTypeAdapter<T> removeAdapterItemView(int viewType) {
        mAdapterItemViewManager.removeAdapterItemView(viewType);
        return this;
    }

    public void setItemList(List<T> items) {
        this.mItems = items;

        notifyDataSetChanged();
    }

    public List<T> getItemList() {
        return mItems;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isDataEmpty() {
        return (mItems == null || mItems.isEmpty());
    }

    public void add(T item) {
        if (mItems == null) {
            return;
        }
        mItems.add(item);
    }

    public void add(int position, T item) {
        if (isPositionInvalid(position) || item == null) {
            return;
        }
        mItems.add(position, item);
    }

    public void add(List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        if (mItems == null) {
            return;
        }
        mItems.addAll(dataList);
    }

    public void remove(T item) {
        if (mItems == null || item == null) {
            return;
        }
        mItems.remove(item);
    }

    public void remove(int position) {
        if (isPositionInvalid(position)) {
            return;
        }
        if (mItems == null) {
            return;
        }
        mItems.remove(position);
    }

    public void clear() {
        if (mItems == null) {
            return;
        }
        mItems.clear();
    }

    private boolean isPositionInvalid(int position) {
        return (position < 0 || position >= getCount());
    }

    public static class AdapterItemViewManager<T> {
        private final SparseArray<AdapterItemView<T>> mAdapterItemViewArray = new SparseArray<>();

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

        public AdapterItemViewManager<T> removeAdapterItemView(int viewType) {
            int indexToRemove = mAdapterItemViewArray.indexOfKey(viewType);
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

        public void convert(ListViewHolder holder, T item, int position) {
            int count = mAdapterItemViewArray.size();
            for (int i = 0; i < count; i++) {
                AdapterItemView<T> adapterItemView = mAdapterItemViewArray.valueAt(i);
                if (adapterItemView.isForViewType(item, position)) {
                    adapterItemView.convert(holder, item, position);
                    break;
                }
            }
        }

        public AdapterItemView<T> getAdapterItemView(T item, int position) {
            int count = mAdapterItemViewArray.size();
            for (int i = count - 1; i >= 0; i--) {
                AdapterItemView<T> adapterItemView = mAdapterItemViewArray.valueAt(i);
                if (adapterItemView.isForViewType(item, position)) {
                    return adapterItemView;
                }
            }
            return null;
        }

        public AdapterItemView<T> getAdapterItemView(int viewType) {
            return mAdapterItemViewArray.get(viewType);
        }

        public int getItemLayoutId(int viewType) {
            return getAdapterItemView(viewType).getItemLayoutId();
        }

        public int getItemViewType(AdapterItemView<T> adapterItemView) {
            return mAdapterItemViewArray.indexOfValue(adapterItemView);
        }
    }

    public interface AdapterItemView<T> {

        int getItemLayoutId();

        boolean isForViewType(T item, int position);

        void convert(ListViewHolder holder, T item, int position);
    }
}
