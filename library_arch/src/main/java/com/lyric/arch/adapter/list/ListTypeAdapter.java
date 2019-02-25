package com.lyric.arch.adapter.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lyric.arch.adapter.AdapterItemView;
import com.lyric.arch.adapter.AdapterItemViewManager;

import java.util.List;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public class ListTypeAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mItems;
    private AdapterItemViewManager<T> mAdapterItemViewManager;

    public ListTypeAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.mItems = items;
        this.mAdapterItemViewManager = new AdapterItemViewManager<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T item = getItem(position);
        AdapterItemView<T> adapterItemView = mAdapterItemViewManager.getAdapterItemView(item, position);
        if (adapterItemView == null) {
            return null;
        }
        ListViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ListViewHolder(mContext, parent, adapterItemView.getItemViewLayoutId());
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
            return mAdapterItemViewManager.getItemViewType(mItems.get(position), position);
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
        return mItems.size();
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

    public ListTypeAdapter<T> addAdapterItemView(AdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.addAdapterItemView(adapterItemView);
        return this;
    }

    public void setItemList(List<T> items) {
        this.mItems = items;
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
}
