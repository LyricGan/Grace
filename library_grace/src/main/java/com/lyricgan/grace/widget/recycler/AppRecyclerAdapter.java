package com.lyricgan.grace.widget.recycler;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public abstract class AppRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private final SparseArray<View> mHeaderViews = new SparseArray<>();
    private final SparseArray<View> mFootViews = new SparseArray<>();

    private final Context mContext;
    private List<T> mItems;
    private final RecyclerAdapterItemViewManager<T> mAdapterItemViewManager;
    private OnAdapterItemClickListener mOnAdapterItemClickListener;
    private OnAdapterItemLongClickListener mOnAdapterItemLongClickListener;

    public interface OnAdapterItemClickListener {

        void onItemClick(ViewGroup parent, View view, int position);
    }

    public interface OnAdapterItemLongClickListener {

        boolean onItemLongClick(ViewGroup parent, View view, int position);
    }

    public AppRecyclerAdapter(Context context) {
        this(context, null);
    }

    public AppRecyclerAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.mItems = items;
        this.mAdapterItemViewManager = new RecyclerAdapterItemViewManager<>();

        addAdapterItemViews();
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
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
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
        RecyclerAdapterItemView<T> adapterItemView = mAdapterItemViewManager.getAdapterItemView(viewType);
        RecyclerViewHolder holder = RecyclerViewHolder.createViewHolder(parent.getContext(), parent, adapterItemView.getItemLayoutId());
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

    protected abstract void addAdapterItemViews();

    public AppRecyclerAdapter<T> addAdapterItemView(RecyclerAdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.addAdapterItemView(adapterItemView);
        return this;
    }

    public AppRecyclerAdapter<T> addAdapterItemView(int viewType, RecyclerAdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.addAdapterItemView(viewType, adapterItemView);
        return this;
    }

    public AppRecyclerAdapter<T> removeAdapterItemView(RecyclerAdapterItemView<T> adapterItemView) {
        mAdapterItemViewManager.removeAdapterItemView(adapterItemView);
        return this;
    }

    public AppRecyclerAdapter<T> removeAdapterItemView(int viewType) {
        mAdapterItemViewManager.removeAdapterItemView(viewType);
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

    public static class RecyclerWrapperUtils {

        public interface SpanSizeCallback {

            int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position);
        }

        public static void onAttachedToRecyclerView(RecyclerView recyclerView, final SpanSizeCallback callback) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
                    }
                });
                gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
            }
        }

        public static void setFullSpan(RecyclerView.ViewHolder holder) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
        }
    }

    public static class RecyclerAdapterItemViewManager<T> {
        private final SparseArray<RecyclerAdapterItemView<T>> mAdapterItemViewArray = new SparseArray<>();

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

        public RecyclerAdapterItemViewManager<T> removeAdapterItemView(int viewType) {
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
                    break;
                }
            }
        }

        public RecyclerAdapterItemView<T> getAdapterItemView(int viewType) {
            return mAdapterItemViewArray.get(viewType);
        }

        public int getItemLayoutId(int viewType) {
            return getAdapterItemView(viewType).getItemLayoutId();
        }

        public int getItemViewType(RecyclerAdapterItemView<T> adapterItemView) {
            return mAdapterItemViewArray.indexOfValue(adapterItemView);
        }
    }

    public interface RecyclerAdapterItemView<T> {

        @LayoutRes int getItemLayoutId();

        boolean isForViewType(T item, int position);

        void convert(RecyclerViewHolder holder, T item, int position);
    }
}