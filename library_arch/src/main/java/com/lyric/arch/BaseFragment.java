package com.lyric.arch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * base fragment
 *
 * @author lyricgan
 */
public abstract class BaseFragment extends Fragment implements IBaseListener, View.OnClickListener {
    private View mRootView;
    private boolean mSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onCreatePrepare(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentViewId(), null);
        mRootView = rootView;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View titleView = view.findViewById(R.id.title_bar);
        onContentCreated(view, savedInstanceState, getArguments(), new BaseTitleBar(titleView));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
    }

    public <T extends View> T findViewById(int id) {
        View rootView = getRootView();
        if (rootView != null) {
            return rootView.findViewById(id);
        }
        return null;
    }

    protected View getRootView() {
        return mRootView;
    }

    @Override
    public void showLoading(CharSequence message) {
        showLoading(message, true);
    }

    @Override
    public void showLoading(CharSequence message, boolean cancelable) {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (!isAdded() || isRemoving()) {
            return;
        }
        activity.showLoading(message, cancelable);
    }

    @Override
    public void hideLoading() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (!isAdded() || isRemoving()) {
            return;
        }
        activity.hideLoading();
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean isActivityFinishing() {
        Activity activity = getActivity();
        return activity == null || activity.isFinishing();
    }

    public void finishActivity() {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        activity.finish();
    }

    @Override
    public Handler getHandler() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return ((BaseActivity) activity).getHandler();
        }
        return null;
    }

    @Override
    public void handleMessage(Message msg) {
    }

    public void onSelectChanged(boolean isSelected) {
        this.mSelected = isSelected;
    }

    public boolean isSelected() {
        return mSelected;
    }
}
