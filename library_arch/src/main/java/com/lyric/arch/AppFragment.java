package com.lyric.arch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * base fragment
 *
 * @author lyricgan
 */
public abstract class AppFragment extends Fragment implements AppListener, View.OnClickListener {
    private View mRootView;
    private AppTitleBar titleBar;
    private boolean mSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onCreatePrepare(savedInstanceState, getArguments());
        super.onCreate(savedInstanceState);
        logMessage("onCreate()");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logMessage("onCreateView()");
        return inflater.inflate(getContentViewId(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logMessage("onViewCreated()");
        this.mRootView = view;
        if (titleBar == null) {
            titleBar = new AppTitleBar(view);
        }
        onCreateTitleBar(titleBar, getArguments());

        onCreateContentView(view, savedInstanceState, getArguments());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logMessage("onActivityCreated()");
        onCreateData(savedInstanceState, getArguments());
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
    public void onCreatePrepare(Bundle savedInstanceState, Bundle args) {
    }

    protected void onCreateTitleBar(AppTitleBar titleBar, Bundle args) {
        titleBar.setLeftTextOnClickListener(mBackPressedClickListener);
        titleBar.setLeftImageOnClickListener(mBackPressedClickListener);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState, Bundle args) {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void showLoading(CharSequence message, boolean cancelable) {
        if (!(getActivity() instanceof AppActivity)) {
            return;
        }
        AppActivity activity = (AppActivity) getActivity();
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
        if (!(getActivity() instanceof AppActivity)) {
            return;
        }
        AppActivity activity = (AppActivity) getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (!isAdded() || isRemoving()) {
            return;
        }
        activity.hideLoading();
    }

    public View getRootView() {
        return mRootView;
    }

    public <T extends View> T findViewById(int id) {
        Activity activity = getActivity();
        if (activity != null) {
            return activity.findViewById(id);
        }
        return null;
    }

    public void onBackPressed() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
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
    }

    public void toast(CharSequence text) {
    }

    private void logMessage(String message) {
        Log.d(getClass().getName(), message);
    }

    protected View.OnClickListener mBackPressedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
