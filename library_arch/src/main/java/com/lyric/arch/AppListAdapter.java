package com.lyric.arch;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 适配器基类{@link BaseAdapter}
 * @author lyricgan
 */
public abstract class AppListAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mItemList;
    private LayoutInflater mInflater;

    public AppListAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public AppListAdapter(Context context, T[] arrays) {
        this(context, Arrays.asList(arrays));
    }

    public AppListAdapter(Context context, List<T> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
        this.mInflater = LayoutInflater.from(context);
    }

	@Override
	public int getCount() {
		return mItemList != null ? mItemList.size() : 0;
	}

	@Override
	public T getItem(int position) {
        if (isPositionInvalid(position)) {
            return null;
        }
        return mItemList.get(position);
    }

	@Override
	public long getItemId(int position) {
		return position;
	}

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(getItemLayoutId(), null);
        }
        T item = getItem(position);
        if (item != null) {
            convertItemView(position, convertView, item);
        }
        return convertView;
    }

    protected abstract @LayoutRes int getItemLayoutId();

    protected abstract void convertItemView(int position, View convertView, T item);

    public Context getContext() {
        return mContext;
    }

    public List<T> getItemList() {
        return mItemList;
    }

    public void setItemList(List<T> itemList) {
        this.mItemList = itemList;
    }

    public boolean isEmpty() {
        return (mItemList == null || mItemList.isEmpty());
    }

    public void add(T item) {
        if (mItemList != null) {
            mItemList.add(item);
        }
    }

    public void add(int position, T item) {
        if (isPositionInvalid(position)) {
            return;
        }
        mItemList.add(position, item);
    }

    public void add(List<T> itemList) {
        if (itemList == null || itemList.isEmpty()) {
            return;
        }
        if (mItemList != null) {
            mItemList.addAll(itemList);
        }
    }

    public void remove(T item) {
        if (mItemList != null) {
            mItemList.remove(item);
        }
    }

    public void remove(int position) {
        if (isPositionInvalid(position)) {
            return;
        }
        if (mItemList != null) {
            mItemList.remove(position);
        }
    }

    public void clear() {
        if (mItemList != null) {
            mItemList.clear();
        }
    }

    private boolean isPositionInvalid(int position) {
        return (position < 0 || position >= getCount());
    }
}
