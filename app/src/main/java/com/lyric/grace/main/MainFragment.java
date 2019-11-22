package com.lyric.grace.main;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseFragment;

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
        ChartView chartView = view.findViewById(R.id.chart_view);

        String[] xTextArray = {"11-21", "11-22", "11-23", "11-24", "11-25", "11-26", "11-27", "11-28", "11-29", "11-30"};
        String[] yTextArray = {"0", "20", "40", "60", "80", "100"};
        String[] dataArray = {"50", "66", "22", "45", "100", "80", "72", "66", "22", "85"};
        chartView.setData(xTextArray, yTextArray, dataArray);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }
}
