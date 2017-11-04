package com.lyric.grace.common;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lyric.grace.R;
import com.lyric.grace.widget.LoadingDialog;
import com.lyric.grace.widget.TitleBar;

/**
 * fragment基类，继承于support v4包下的Fragment
 * @author lyricgan
 * @date 2017/10/26 10:25
 */
public abstract class BaseFragment extends Fragment implements IBaseListener, ILoadingListener, IMessageProcessor {
    /** 是否对用户可见 */
    private boolean mIsVisibleToUser;
    private LoadingDialog mLoadingDialog;
    private View mRootView;
    private TitleBar mTitleBar;
    private BaseHandler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onPrepareCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        if (isUseTitleBar()) {
            Context context = mRootView.getContext();
            adjustTitleBar(context);
            LinearLayout rootLayout = new LinearLayout(context);
            rootLayout.setOrientation(LinearLayout.VERTICAL);
            rootLayout.addView(mTitleBar, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rootLayout.addView(mRootView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mRootView = rootLayout;
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onLayoutCreated(savedInstanceState);
    }

    @Override
    public void onPrepareCreate(Bundle savedInstanceState) {
        mHandler = new BaseHandler(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            initExtras(arguments);
        }
    }

    protected void initExtras(Bundle bundle) {
    }

    private void adjustTitleBar(Context context) {
        mTitleBar = new TitleBar(context);
        onTitleBarCreated(mTitleBar);
    }

    protected boolean isUseTitleBar() {
        return true;
    }

    protected void onTitleBarCreated(TitleBar titleBar) {
        titleBar.setLeftDrawable(R.drawable.icon_back);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleBarLeftClick();
            }
        });
    }

    protected TitleBar getTitleBar() {
        return mTitleBar;
    }

    protected void onTitleBarLeftClick() {
        onBackPressed();
    }

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void onLayoutCreated(Bundle savedInstanceState);

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    @Override
    public void showLoading(CharSequence message, boolean isCancelable) {
        if (isActivityFinishing()) {
            return;
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
        }
        mLoadingDialog.setMessage(message);
        mLoadingDialog.setCancelable(isCancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    protected void showLoading(CharSequence message) {
        showLoading(message, true);
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

    @Override
    public void handleMessage(Message msg) {
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }
}
