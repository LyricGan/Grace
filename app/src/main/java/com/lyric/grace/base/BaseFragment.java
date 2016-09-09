package com.lyric.grace.base;

import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {
    private boolean mInterceptVisibleHint;

    public BaseFragment() {
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
}
