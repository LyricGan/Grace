package com.lyric.grace.main;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseFragment;

/**
 * @author lyricgan
 * @since 2019/2/15
 */
public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.btn_play).setOnClickListener(this);
        view.findViewById(R.id.btn_pause).setOnClickListener(this);
        view.findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
        super.onCreateData(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_play:
                break;
            case R.id.btn_pause:
                break;
            case R.id.btn_stop:
                break;
        }
    }
}
