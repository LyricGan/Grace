package com.lyric.grace.activity;

import android.os.Bundle;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseCompatActivity;
import com.lyric.grace.view.TitleBar;

/**
 * @author lyric
 * @description
 * @time 2016/5/30 12:38
 */
public class LoadingActivity extends BaseCompatActivity {

    @Override
    public void onTitleCreated(TitleBar titleBar) {
        titleBar.setText("Loading");
    }

    @Override
    public void onViewCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_loading);
    }
}
