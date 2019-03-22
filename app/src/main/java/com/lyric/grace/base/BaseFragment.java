package com.lyric.grace.base;

import android.os.Bundle;
import android.view.View;

import com.lyric.arch.app.AppActivity;
import com.lyric.arch.app.AppFragment;
import com.lyric.arch.app.AppTitleBar;
import com.lyric.grace.R;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public abstract class BaseFragment extends AppFragment {
    private boolean mSelected;

    @Override
    public void onCreatePrepare(Bundle savedInstanceState) {
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, Bundle args) {
    }

    @Override
    protected void onCreateTitleBar(AppTitleBar titleBar, Bundle savedInstanceState) {
        titleBar.setLeftTextOnClickListener(this);
        titleBar.setLeftImageOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_text || v.getId() == R.id.title_bar_left_image) {
            onBackPressed();
        }
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

    public void onSelectChanged(boolean isSelected) {
        this.mSelected = isSelected;
    }

    public boolean isSelected() {
        return mSelected;
    }
}
