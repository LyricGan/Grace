package com.lyric.grace.news;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseFragment;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public class NewsFragment extends BaseFragment {

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {

    }
}
