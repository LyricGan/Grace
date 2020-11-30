package com.lyric.grace.samples;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.samples.app.BaseFragment;
import com.lyric.grace.samples.app.BaseFragmentActivity;
import com.lyric.grace.samples.util.AppRunningUtils;
import com.lyric.grace.samples.util.PageJumpHelper;

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
        view.findViewById(R.id.tv_test_1).setOnClickListener(v -> PageJumpHelper.jumpFragmentPage(getActivity(), NestedScrollFragment.class, BaseFragmentActivity.class, "NestedPage", null));
        view.findViewById(R.id.tv_test_2).setOnClickListener(v -> {
            if (AppRunningUtils.isIgnoringBatteryOptimizations(getActivity())) {
                AppRunningUtils.jumpBatteryOptimizationsSettings(getActivity());
            } else {
                AppRunningUtils.requestIgnoreBatteryOptimizations(getActivity());
            }
        });
        view.findViewById(R.id.tv_test_3).setOnClickListener(v -> AppRunningUtils.handleBackgroundRunningSettings(getActivity()));
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }
}
