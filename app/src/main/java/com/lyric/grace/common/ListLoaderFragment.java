package com.lyric.grace.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aspsine.irecyclerview.OnItemClickListener;
import com.aspsine.irecyclerview.view.RecyclerAdapter;
import com.aspsine.irecyclerview.view.RefreshListView;
import com.lyric.grace.R;
import com.lyric.grace.entity.BaseListEntity;

import retrofit2.Call;

/**
 * @author lyricgan
 * @description 列表加载Fragment基类
 * @time 2016/11/3 15:05
 */
public abstract class ListLoaderFragment<T> extends BaseFragment {
    private ListLoader<T> mLoader;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void onLayoutCreated(Bundle savedInstanceState) {
        View layoutView = getView();
        if (layoutView == null) {
            return;
        }
        RefreshListView refreshListView = (RefreshListView) layoutView.findViewById(R.id.refresh_list_view);
        RecyclerAdapter<T> adapter = getAdapter();
        if (adapter != null) {
            adapter.setOnItemClickListener(new OnItemClickListener<T>() {
                @Override
                public void onItemClick(int position, T item, View view) {
                    onListItemClick(position, item, view);
                }
            });
        }
        mLoader = new ListLoader<>(getActivity(), refreshListView, adapter);
        mLoader.setOnListLoadMoreListener(new ListLoader.OnListLoadMoreListener<T>() {
            @Override
            public void loadMore(ListLoader<T> loader, int pageIndex, int pageSize) {
                loader.load(getCall(pageIndex, pageSize));
            }
        });
        onLayoutLoad(savedInstanceState);
    }

    protected void load() {
        getLoader().getRefreshListView().showLoading();
        getLoader().load(getCall(ListLoader.DEFAULT_INDEX, ListLoader.DEFAULT_SIZE));
    }

    protected ListLoader<T> getLoader() {
        return mLoader;
    }

    protected void onListItemClick(int position, T item, View view) {
    }

    protected abstract RecyclerAdapter<T> getAdapter();

    protected abstract Call<BaseListEntity<T>> getCall(int pageIndex, int pageSize);

    protected abstract void onLayoutLoad(@Nullable Bundle savedInstanceState);
}
