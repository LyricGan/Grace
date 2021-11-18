package com.lyricgan.grace.samples.app;

import android.os.Bundle;
import android.view.View;

import com.lyricgan.base.GraceFragment;
import com.lyricgan.grace.samples.widget.TitleBar;

public abstract class BaseFragment extends GraceFragment {

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, Bundle args) {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void showLoading(CharSequence message, boolean cancelable) {
        if (!(getActivity() instanceof BaseActivity)) {
            return;
        }
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
        if (!(getActivity() instanceof BaseActivity)) {
            return;
        }
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

    public TitleBar getActivityTitleBar() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getTitleBar();
        }
        return null;
    }
}
