package com.lyric.arch.widget.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView适配器基类
 * @author lyricgan
 */
public abstract class RecyclerSimpleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<T> mItemList;
    private LayoutInflater mInflater;
    private OnItemClickListener<T> mOnItemClickListener;

    private static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<T> {

        void onItemClick(int position, T item, View itemView);
    }

    public RecyclerSimpleAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public RecyclerSimpleAdapter(Context context, T[] arrays) {
        this(context, Arrays.asList(arrays));
    }

    public RecyclerSimpleAdapter(Context context, List<T> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = getItemView(parent, viewType, mInflater);
        final RecyclerView.ViewHolder holder = new RecyclerViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    T item = getItem(position);
                    if (item != null) {
                        mOnItemClickListener.onItemClick(position, item, v);
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        T item = getItem(position);
        if (item != null) {
            convertItemView(holder.itemView, position, item);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList != null ? mItemList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected View getItemView(ViewGroup parent, int viewType, LayoutInflater inflater) {
        return inflater.inflate(getItemLayoutId(), parent, false);
    }
    
    protected abstract @LayoutRes int getItemLayoutId();

    protected abstract void convertItemView(View itemView, int position, T item);


    public T getItem(int position) {
        if (isPositionInvalid(position)) {
            return null;
        }
        return mItemList.get(position);
    }

    public Context getContext() {
        return mContext;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setItemList(List<T> itemList) {
        this.mItemList = itemList;
    }

    public List<T> getItemList() {
        return mItemList;
    }

    public boolean isEmpty() {
        return (mItemList == null || mItemList.isEmpty());
    }

    public void add(T item) {
        if (mItemList == null) {
            return;
        }
        mItemList.add(item);
    }

    public void add(int position, T item) {
        if (isPositionInvalid(position) || item == null) {
            return;
        }
        mItemList.add(position, item);
    }

    public void add(List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        if (mItemList == null) {
            return;
        }
        mItemList.addAll(dataList);
    }

    public void remove(T item) {
        if (mItemList == null || item == null) {
            return;
        }
        mItemList.remove(item);
    }

    public void remove(int position) {
        if (isPositionInvalid(position)) {
            return;
        }
        if (mItemList == null) {
            return;
        }
        mItemList.remove(position);
    }

    public void clear() {
        if (mItemList == null) {
            return;
        }
        mItemList.clear();
    }
    
    private boolean isPositionInvalid(int position) {
        return (position < 0 || position >= getItemCount());
    }
}
