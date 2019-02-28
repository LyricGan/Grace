package com.lyric.arch.widget.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public class RecyclerTypeAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context mContext;
    private List<T> mItems;
    private RecyclerAdapterItemViewManager<T> mAdapterItemViewManager;
    private OnAdapterItemClickListener<T> mOnAdapterItemClickListener;
    private OnAdapterItemLongClickListener<T> mOnAdapterItemLongClickListener;

    public RecyclerTypeAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.mItems = items;
        this.mAdapterItemViewManager = new RecyclerAdapterItemViewManager<>();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerAdapterItemView adapterItemView = mAdapterItemViewManager.getAdapterItemView(viewType);
        RecyclerViewHolder holder = getViewHolder(mContext, parent, adapterItemView.getItemViewLayoutId());
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        convert(holder, mItems.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (isUseAdapterItemViewManager()) {
            return mAdapterItemViewManager.getItemViewType(getItem(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }
    
    protected RecyclerViewHolder getViewHolder(Context context, ViewGroup parent, int layoutId) {
        return RecyclerViewHolder.createViewHolder(context, parent, layoutId);
    }

    protected void onViewHolderCreated(RecyclerViewHolder holder, View itemView) {
    }

    protected void convert(RecyclerViewHolder holder, T item) {
        mAdapterItemViewManager.convert(holder, item, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(final ViewGroup parent, final RecyclerViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }
        View convertView = viewHolder.getConvertView();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAdapterItemClickListener == null) {
                    return;
                }
                int position = viewHolder.getAdapterPosition();
                T item = getItem(position);
                if (item == null) {
                    return;
                }
                mOnAdapterItemClickListener.onItemClick(parent, v, position, item);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnAdapterItemLongClickListener == null) {
                    return false;
                }
                int position = viewHolder.getAdapterPosition();
                T item = getItem(position);
                if (item == null) {
                    return false;
                }
                return mOnAdapterItemLongClickListener.onItemLongClick(parent, v, position, item);
            }
        });
    }

    protected boolean isUseAdapterItemViewManager() {
        return mAdapterItemViewManager.getItemViewCount() > 0;
    }

    public RecyclerTypeAdapter<T> addAdapterItemView(RecyclerAdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.addAdapterItemView(adapterItemView);
        return this;
    }

    public RecyclerTypeAdapter addAdapterItemView(int viewType, RecyclerAdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.addAdapterItemView(viewType, adapterItemView);
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

    public T getItem(int position) {
        if (isPositionInvalid(position)) {
            return null;
        }
        return mItems.get(position);
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
        return (position < 0 || position >= getItemCount());
    }

    public void setOnItemClickListener(OnAdapterItemClickListener<T> listener) {
        this.mOnAdapterItemClickListener = listener;
    }

    public void setOnAdapterItemLongClickListener(OnAdapterItemLongClickListener<T> listener) {
        this.mOnAdapterItemLongClickListener = listener;
    }

    public interface OnAdapterItemClickListener<T> {

        void onItemClick(ViewGroup parent, View view, int position, T item);
    }

    public interface OnAdapterItemLongClickListener<T> {

        boolean onItemLongClick(ViewGroup parent, View view, int position, T item);
    }
}