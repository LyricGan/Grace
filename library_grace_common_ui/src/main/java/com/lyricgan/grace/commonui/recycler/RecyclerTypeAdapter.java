package com.lyricgan.grace.commonui.recycler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerTypeAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private Context mContext;
    private List<T> mItems;
    private RecyclerAdapterItemViewManager<T> mAdapterItemViewManager;
    private OnAdapterItemClickListener mOnAdapterItemClickListener;
    private OnAdapterItemLongClickListener mOnAdapterItemLongClickListener;

    public interface OnAdapterItemClickListener {

        void onItemClick(ViewGroup parent, View view, int position);
    }

    public interface OnAdapterItemLongClickListener {

        boolean onItemLongClick(ViewGroup parent, View view, int position);
    }

    public RecyclerTypeAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.mItems = items;
        this.mAdapterItemViewManager = new RecyclerAdapterItemViewManager<>();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return RecyclerViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
        } else if (mFootViews.get(viewType) != null) {
            return RecyclerViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
        } else {
            return getCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        convert(holder, position - getHeaderViewCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeaderViewCount() - getContentItemSize());
        }
        position = position - getHeaderViewCount();
        if (isUseAdapterItemViewManager()) {
            return mAdapterItemViewManager.getItemViewType(getItem(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return getContentItemSize() + getHeaderViewCount() + getFooterViewCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerWrapperUtils.onAttachedToRecyclerView(recyclerView, new RecyclerWrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            RecyclerWrapperUtils.setFullSpan(holder);
        }
    }

    protected RecyclerViewHolder getCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerAdapterItemView adapterItemView = mAdapterItemViewManager.getAdapterItemView(viewType);
        RecyclerViewHolder holder = RecyclerViewHolder.createViewHolder(parent.getContext(), parent, adapterItemView.getItemViewLayoutId());
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    protected void onViewHolderCreated(RecyclerViewHolder holder, View itemView) {
    }

    protected void convert(RecyclerViewHolder holder, int position) {
        T item = getItem(position);
        if (item != null) {
            mAdapterItemViewManager.convert(holder, item, position);
        }
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
                mOnAdapterItemClickListener.onItemClick(parent, v, viewHolder.getAdapterPosition() - getHeaderViewCount());
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnAdapterItemLongClickListener == null) {
                    return false;
                }
                return mOnAdapterItemLongClickListener.onItemLongClick(parent, v, viewHolder.getAdapterPosition() - getHeaderViewCount());
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

    public int getContentItemSize() {
        return mItems != null ? mItems.size() : 0;
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeaderViewCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeaderViewCount() + getContentItemSize();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeaderViewCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewCount() {
        return mFootViews.size();
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
        return (position < 0 || position >= getContentItemSize());
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.mOnAdapterItemClickListener = listener;
    }

    public void setOnAdapterItemLongClickListener(OnAdapterItemLongClickListener listener) {
        this.mOnAdapterItemLongClickListener = listener;
    }
}