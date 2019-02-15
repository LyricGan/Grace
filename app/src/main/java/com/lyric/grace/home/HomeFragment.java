package com.lyric.grace.home;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseFragment;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {

    }
}
