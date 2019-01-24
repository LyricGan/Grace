package com.lyric.arch;

import android.content.Context;
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
public abstract class AppRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<T> mDataList;
    private int mLayoutId;
    private LayoutInflater mInflater;
    private OnItemClickListener<T> mOnItemClickListener;

    public AppRecyclerAdapter(Context context, int layoutId) {
        this(context, new ArrayList<T>(), layoutId);
    }

    public AppRecyclerAdapter(Context context, T[] arrays, int layoutId) {
        this(context, Arrays.asList(arrays), layoutId);
    }

    public AppRecyclerAdapter(Context context, List<T> dataList, int layoutId) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mLayoutId = layoutId;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        final RecyclerView.ViewHolder holder = new RecyclerViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                T object = null;
                if (mDataList != null && !mDataList.isEmpty()) {
                    object = mDataList.get(position);
                }
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, object, v);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        T object = null;
        if (mDataList != null && !mDataList.isEmpty()) {
            object = mDataList.get(position);
        }
        convert(holder.itemView, position, object);
    }

    public abstract void convert(View itemView, int position, T item);

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Context getContext() {
        return mContext;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setDataList(List<T> dataList) {
        this.mDataList = dataList;
    }

    public List<T> getDataList() {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        return mDataList;
    }

    public boolean isEmpty() {
        return (mDataList == null || mDataList.isEmpty());
    }

    public void add(T object) {
        add(mDataList.size(), object);
    }

    public void add(int location, T object) {
        if (location < 0 || location > mDataList.size()) {
            return;
        }
        if (object != null) {
            mDataList.add(location, object);
            notifyDataSetChanged();
        }
    }

    public void add(List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void remove(T object) {
        if (isEmpty()) {
            return;
        }
        if (object != null) {
            mDataList.remove(object);
            notifyDataSetChanged();
        }
    }

    public void remove(int location) {
        if (isEmpty()) {
            return;
        }
        if (location < 0 || location >= mDataList.size()) {
            return;
        }
        mDataList.remove(location);
        notifyDataSetChanged();
    }

    public void clear() {
        if (isEmpty()) {
            return;
        }
        mDataList.clear();
        notifyDataSetChanged();
    }

    private static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<T> {

        void onItemClick(int position, T object, View itemView);
    }
}
