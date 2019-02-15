package com.lyric.grace.mine;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseFragment;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {

    }
}
