package com.lyric.grace.common;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.view.EmptyView;
import com.aspsine.irecyclerview.view.LoadMoreFooterView;
import com.aspsine.irecyclerview.view.RecyclerAdapter;
import com.aspsine.irecyclerview.view.RefreshListView;
import com.lyric.grace.R;
import com.lyric.grace.api.ApiParams;
import com.lyric.grace.api.ResponseCallback;
import com.lyric.grace.api.ResponseHandler;
import com.lyric.grace.entity.BaseListEntity;
import com.lyric.grace.entity.BaseListWrapperEntity;
import com.lyric.grace.logger.Loggers;

import java.util.List;

import retrofit2.Call;

/**
 * @author lyricgan
 * @description 列表加载工具类，封装下拉刷新，上拉加载功能
 * @time 2016/11/3 10:10
 */
public class ListLoader<T> {
    public static final int DEFAULT_INDEX = ApiParams.PAGE_INDEX;
    public static final int DEFAULT_SIZE = ApiParams.PAGE_SIZE;
    private int mPageIndex = DEFAULT_INDEX;
    private int mPageSize = DEFAULT_SIZE;
    private Activity mActivity;
    private RefreshListView mRefreshListView;
    private RecyclerAdapter<T> mAdapter;
    private Call<BaseListEntity<T>> mDefaultCall;
    private Call<BaseListEntity<T>> mCall;
    // 标识列表是否存在更多数据
    private boolean mHasMore;
    // 列表加载更多监听事件
    private OnListLoadMoreListener<T> mLoadMoreListener;
    // 列表加载回调监听事件
    private OnListLoadCallbackListener<T> mCallbackListener;
    // 空数据提示信息
    private String mEmptyText;

    public ListLoader(Activity activity, RefreshListView refreshListView, RecyclerAdapter<T> adapter) {
        this(activity, refreshListView, adapter, true, true);
    }

    public ListLoader(Activity activity, RefreshListView refreshListView, RecyclerAdapter<T> adapter, boolean refreshEnabled, boolean loadMoreEnabled) {
        this.mActivity = activity;
        this.mRefreshListView = refreshListView;
        this.mAdapter = adapter;

        initialize(refreshEnabled, loadMoreEnabled);
    }

    private void initialize( boolean refreshEnabled, boolean loadMoreEnabled) {
        mRefreshListView.setRefreshEnabled(refreshEnabled);
        mRefreshListView.setLoadMoreEnabled(loadMoreEnabled);
        mRefreshListView.setAdapter(mAdapter);
        mRefreshListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
        mRefreshListView.setOnRetryListener(new EmptyView.OnRetryListener() {
            @Override
            public void onRetry(EmptyView view) {
                mRefreshListView.showLoading();
                reload();
            }
        });
        mRefreshListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(View loadMoreView) {
                if (mHasMore && !mRefreshListView.isLoadMoreLoading()) {
                    mRefreshListView.showLoadMoreLoading(true);
                    if (mLoadMoreListener != null) {
                        mLoadMoreListener.loadMore(ListLoader.this, mPageIndex, mPageSize);
                    }
                }
            }
        });
        mRefreshListView.setOnLoadMoreRetryListener(new LoadMoreFooterView.OnRetryListener() {
            @Override
            public void onRetry(LoadMoreFooterView view) {
                mRefreshListView.showLoadMoreLoading(true);
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.loadMore(ListLoader.this, mPageIndex, mPageSize);
                }
            }
        });
    }

    public void setRefreshEnabled(boolean enabled) {
        mRefreshListView.setRefreshEnabled(enabled);
    }

    public void setLoadMoreEnabled(boolean enabled) {
        mRefreshListView.setLoadMoreEnabled(enabled);
    }

    public RefreshListView getRefreshListView() {
        return mRefreshListView;
    }

    public RecyclerAdapter<T> getAdapter() {
        return mAdapter;
    }

    public void setEmptyText(String text) {
        this.mEmptyText = text;
    }

    public void load(Call<BaseListEntity<T>> call) {
        if (call == null) {
            return;
        }
        if (mDefaultCall == null) {
            this.mDefaultCall = call;
        }
        this.mCall = call;
        call.enqueue(new ResponseCallback<BaseListEntity<T>>() {
            @Override
            public void onResponse(Call<BaseListEntity<T>> call, BaseListEntity<T> response) {
                if (mActivity.isFinishing()) {
                    return;
                }
                mRefreshListView.showContent();
                mRefreshListView.setRefreshing(false);
                mRefreshListView.showLoadMoreLoading(false);
                BaseListWrapperEntity<T> wrapperEntity = response.getResult();
                if (wrapperEntity != null) {
                    List<T> dataList = wrapperEntity.getData();
                    if (dataList == null || dataList.isEmpty()) {
                        if (isRefresh()) {
                            if (mAdapter.isEmpty()) {
                                if (TextUtils.isEmpty(mEmptyText)) {
                                    mRefreshListView.showEmpty();
                                } else {
                                    mRefreshListView.showEmpty(mEmptyText);
                                }
                            } else {
                                Loggers.e("server error");
                            }
                        } else {
                            mRefreshListView.showLoadMoreError();
                        }
                    } else {
                        if (isRefresh()) {
                            mAdapter.clear();
                        }
                        mAdapter.add(dataList);
                        mHasMore = wrapperEntity.hasMore();
                        if (mHasMore) {
                            mPageIndex = wrapperEntity.getStart();
                        } else {
                            mRefreshListView.showLoadMoreEnd();
                        }
                    }
                } else {
                    if (isRefresh()) {
                        if (mAdapter.isEmpty()) {
                            mRefreshListView.showEmpty(R.string.network_not_connected_tips);
                        } else {
                            ResponseHandler.process(response.getErrorCode(), response.getErrorMessage());
                        }
                    } else {
                        mRefreshListView.showLoadMoreError();
                    }
                }
                if (mCallbackListener != null) {
                    mCallbackListener.callback(ListLoader.this);
                }
            }

            @Override
            public void onError(Call<BaseListEntity<T>> call, String errorMessage) {
                if (mActivity.isFinishing()) {
                    return;
                }
                mRefreshListView.showContent();
                mRefreshListView.setRefreshing(false);
                mRefreshListView.showLoadMoreLoading(false);
                if (isRefresh()) {
                    if (mAdapter.isEmpty()) {
                        mRefreshListView.showEmpty(R.string.network_not_connected_tips);
                    } else {
                        ResponseHandler.process(errorMessage);
                    }
                } else {
                    mRefreshListView.showLoadMoreError();
                }
                if (mCallbackListener != null) {
                    mCallbackListener.callback(ListLoader.this);
                }
            }
        });
    }

    private boolean isRefresh() {
        return (DEFAULT_INDEX == mPageIndex);
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        this.mPageSize = pageSize;
    }

    public void reload() {
        if (mDefaultCall == null) {
            return;
        }
        mPageIndex = DEFAULT_INDEX;
        Call<BaseListEntity<T>> call = mDefaultCall.clone();
        load(call);
    }

    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    public void setOnListLoadMoreListener(OnListLoadMoreListener<T> listener) {
        this.mLoadMoreListener = listener;
    }

    public void setOnListLoadCallbackListener(OnListLoadCallbackListener<T> listener) {
        this.mCallbackListener = listener;
    }

    public interface OnListLoadMoreListener<T> {

        void loadMore(ListLoader<T> loader, int pageIndex, int pageSize);
    }

    public interface OnListLoadCallbackListener<T> {

        void callback(ListLoader<T> loader);
    }
}
