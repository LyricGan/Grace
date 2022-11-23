package com.lyricgan.grace.samples.app;

import android.app.Activity;
import android.os.Bundle;

import com.lyricgan.arch.GraceFragment;

public abstract class BaseFragment extends GraceFragment {

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, Bundle args) {
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

    public TitleBar getTitleBar() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return ((BaseActivity) activity).getTitleBar();
        }
        return null;
    }
}
