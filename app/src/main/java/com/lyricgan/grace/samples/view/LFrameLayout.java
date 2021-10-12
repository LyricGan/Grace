package com.lyricgan.grace.samples.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LFrameLayout extends NestedFrameLayout implements ILinkageScroll {
    private ChildLinkageEvent mLinkageEvent;
    private RecyclerView mRecyclerView;

    public LFrameLayout(Context context) {
        this(context, null);
    }

    public LFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mRecyclerView = (RecyclerView) getChildAt(0);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);

        // 滚动监听，将必要事件传递给联动容器
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!mRecyclerView.canScrollVertically(-1)) {
                    if (mLinkageEvent != null) {
                        mLinkageEvent.onContentScrollToTop(LFrameLayout.this);
                    }
                }
                if (!mRecyclerView.canScrollVertically(1)) {
                    if (mLinkageEvent != null) {
                        mLinkageEvent.onContentScrollToBottom(LFrameLayout.this);
                    }
                }
                if (mLinkageEvent != null) {
                    mLinkageEvent.onContentScroll(LFrameLayout.this);
                }
            }
        });
    }

    @Override
    public void setChildLinkageEvent(ChildLinkageEvent event) {
        mLinkageEvent = event;
    }

    @Override
    public LinkageScrollHandler provideScrollHandler() {
        return new LinkageScrollHandler() {
            @Override
            public void flingContent(View target, int velocityY) {
                mRecyclerView.fling(0, velocityY);
            }

            @Override
            public void scrollContentToTop() {
                mRecyclerView.scrollToPosition(0);
            }

            @Override
            public void scrollContentToBottom() {
                RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                if (adapter != null && adapter.getItemCount() > 0) {
                    mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
                }
            }

            @Override
            public void stopContentScroll(View target) {
                mRecyclerView.stopScroll();
            }

            @Override
            public boolean canScrollVertically(int direction) {
                return mRecyclerView.canScrollVertically(direction);
            }

            @Override
            public boolean isScrollable() {
                return true;
            }

            @Override
            public int getVerticalScrollExtent() {
                return mRecyclerView.computeVerticalScrollExtent();
            }

            @Override
            public int getVerticalScrollOffset() {
                return mRecyclerView.computeVerticalScrollOffset();
            }

            @Override
            public int getVerticalScrollRange() {
                return mRecyclerView.computeVerticalScrollRange();
            }
        };
    }
}
