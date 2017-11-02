package com.lyric.grace.ui;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.common.BaseCompatActivity;
import com.lyric.grace.widget.TitleBar;

/**
 * @author lyricgan
 * @time 2016/9/1 15:47
 */
public class MainActivity extends BaseCompatActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onLayoutCreated(Bundle savedInstanceState) {
    }

    @Override
    public void onTitleCreated(TitleBar titleBar) {
        titleBar.setLeftVisibility(View.GONE);
    }
}
