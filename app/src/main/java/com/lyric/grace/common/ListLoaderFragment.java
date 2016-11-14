package com.lyric.grace.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class ListLoaderFragment<T> extends Fragment {
    private RefreshListView refreshListView;
    private ListLoader<T> mLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        refreshListView = (RefreshListView) view.findViewById(R.id.refresh_list_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        onActivityLoad(savedInstanceState);
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

    protected abstract void onActivityLoad(@Nullable Bundle savedInstanceState);
}
