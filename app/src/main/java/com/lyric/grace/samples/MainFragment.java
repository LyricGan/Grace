package com.lyric.grace.samples;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;

public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.main_tab_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.btn_jump).setOnClickListener(v -> {
            PageJumpHelper.jumpFragmentPage(getActivity(), NestedScrollFragment.class, BaseFragmentActivity.class, "NestedPage", null);
        });
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }
}
