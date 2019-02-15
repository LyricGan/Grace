package com.lyric.grace.find;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseFragment;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public class FindFragment extends BaseFragment {

    public static FindFragment newInstance() {
        Bundle args = new Bundle();
        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_find;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {

    }
}
