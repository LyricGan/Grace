package com.lyric.grace.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyric.grace.widget.dialog.LoadingDialog;

/**
 * fragment基类
 * @author lyricgan
 * @time 2017/10/26 10:25
 */
public abstract class BaseFragment extends Fragment implements IBaseListener {
    /** 是否对用户可见 */
    private boolean mIsVisibleToUser;
    private LoadingDialog mLoadingDialog;
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onPrepareCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onLayoutCreated(savedInstanceState);
    }

    @Override
    public void onPrepareCreate(Bundle savedInstanceState) {
    }

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void onLayoutCreated(Bundle savedInstanceState);

    @Override
    public void onClick(View v) {
    }

    public boolean isActivityFinishing() {
        return (getActivity() == null) || getActivity().isFinishing();
    }

    public boolean onBackPressed(){
        return false;
    }

    public void onSelect() {
        onSelect(true);
    }

    /**
     * 选中回调
     * @param isSelected 是否选中
     */
    public void onSelect(boolean isSelected) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        try {
            super.setUserVisibleHint(isVisibleToUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mIsVisibleToUser = isVisibleToUser;
    }

    public boolean isVisibleToUser() {
        return this.mIsVisibleToUser;
    }

    protected void showLoading(CharSequence message) {
        if (isActivityFinishing()) {
            return;
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
        }
        mLoadingDialog.setMessage(message);
        mLoadingDialog.show();
    }

    protected void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    protected View getRootView() {
        return mRootView;
    }

    protected <T extends View> T findViewById(int id) {
        T view = null;
        if (mRootView != null) {
            view = (T) mRootView.findViewById(id);
        }
        return view;
    }
}
