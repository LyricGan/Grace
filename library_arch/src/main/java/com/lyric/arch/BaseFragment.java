package com.lyric.arch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyric.utils.LogUtils;
import com.lyric.utils.ToastUtils;

/**
 * base fragment
 *
 * @author lyricgan
 */
public abstract class BaseFragment extends Fragment implements IBaseListener, View.OnClickListener {
    private View mRootView;
    private AppTitleBar titleBar;
    private boolean mSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onCreatePrepare(savedInstanceState);
        super.onCreate(savedInstanceState);
        logMessage("onCreate()");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logMessage("onCreateView()");
        View rootView = inflater.inflate(getContentViewId(), null);
        mRootView = rootView;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logMessage("onViewCreated()");
        View titleView = view.findViewById(R.id.title_bar);
        if (titleBar == null) {
            titleBar = new AppTitleBar(titleView);
            titleBar.setOnClickListener(this);
        }
        onCreateContentView(view, savedInstanceState, getArguments(), titleBar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logMessage("onActivityCreated()");
        onCreateData(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logMessage("onAttach()");
    }

    @Override
    public void onStart() {
        super.onStart();
        logMessage("onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        logMessage("onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        logMessage("onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        logMessage("onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logMessage("onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logMessage("onDetach()");
    }

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.title_bar_left_text || viewId == R.id.title_bar_left_image) {
            onBackPressed();
        }
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

    public View getRootView() {
        return mRootView;
    }

    public <T extends View> T findViewById(int id) {
        View rootView = getRootView();
        if (rootView != null) {
            return rootView.findViewById(id);
        }
        return null;
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

    public void onSelectChanged(boolean isSelected) {
        this.mSelected = isSelected;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void toast(int resId) {
        ToastUtils.show(getActivity(), resId);
    }

    public void toast(CharSequence text) {
        ToastUtils.show(getActivity(), text);
    }

    private void logMessage(String message) {
        LogUtils.d(getClass().getName(), message);
    }
}
