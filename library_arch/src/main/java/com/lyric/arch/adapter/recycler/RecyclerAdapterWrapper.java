package com.lyric.arch.adapter.recycler;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public class RecyclerAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    public RecyclerAdapterWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return RecyclerViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
        } else if (mFootViews.get(viewType) != null) {
            return RecyclerViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        mAdapter.onBindViewHolder(holder, position - getHeaderViewCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeaderViewCount() - getAdapterItemCount());
        }
        return mAdapter.getItemViewType(position - getHeaderViewCount());
    }

    @Override
    public int getItemCount() {
        return getHeaderViewCount() + getFooterViewCount() + getAdapterItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerWrapperUtils.onAttachedToRecyclerView(mAdapter, recyclerView, new RecyclerWrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        mAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            RecyclerWrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeaderViewCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeaderViewCount() + getAdapterItemCount();
    }

    private int getAdapterItemCount() {
        return mAdapter.getItemCount();
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
}
