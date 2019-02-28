package com.lyric.arch.widget.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public class RecyclerAdapterEmptyWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;
    private RecyclerView.Adapter mAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;

    public RecyclerAdapterEmptyWrapper(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mAdapter.getItemCount() == 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isEmpty()) {
            RecyclerViewHolder holder;
            if (mEmptyView != null) {
                holder = RecyclerViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                holder = RecyclerViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isEmpty()) {
            return;
        }
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerWrapperUtils.onAttachedToRecyclerView(mAdapter, recyclerView, new RecyclerWrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isEmpty()) {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        mAdapter.onViewAttachedToWindow(holder);
        if (isEmpty()) {
            RecyclerWrapperUtils.setFullSpan(holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        }
        return mAdapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (isEmpty()) {
            return 1;
        }
        return mAdapter.getItemCount();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }
}
