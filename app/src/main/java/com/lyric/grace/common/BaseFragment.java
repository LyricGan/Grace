package com.lyric.grace.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements View.OnClickListener, IBaseListener {
    private boolean mInterceptVisibleHint;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onPrepareCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onLayoutCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        try {
            super.setUserVisibleHint(isVisibleToUser);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mInterceptVisibleHint && this.getView() != null) {
                getView().setVisibility(isVisibleToUser ? View.VISIBLE : View.GONE);
            }
        }
    }

    public void setInterceptVisibleHint(boolean visibleHint) {
        this.mInterceptVisibleHint = visibleHint;
    }

    public boolean isFinishing() {
        return (getActivity() == null) || getActivity().isFinishing();
    }

    @Override
    public void onClick(View v) {
        onViewClick(v);
    }

    @Override
    public void onPrepareCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onViewClick(View v) {
    }

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void onLayoutCreated(Bundle savedInstanceState);
}
