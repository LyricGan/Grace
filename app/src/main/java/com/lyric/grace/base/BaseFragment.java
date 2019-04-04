package com.lyric.grace.base;

import android.os.Bundle;
import android.view.View;

import com.lyric.support.app.AppActivity;
import com.lyric.support.app.AppFragment;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public abstract class BaseFragment extends AppFragment {

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

    public boolean onBackPressed() {
        return false;
    }
}
