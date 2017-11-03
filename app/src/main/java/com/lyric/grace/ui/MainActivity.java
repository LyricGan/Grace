package com.lyric.grace.ui;

import android.os.Bundle;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.common.BaseCompatActivity;
import com.lyric.grace.widget.TitleBar;

/**
 * 应用主页面
 * @author lyricgan
 * @time 2016/9/1 15:47
 */
public class MainActivity extends BaseCompatActivity {

    @Override
    protected void onTitleBarCreated(TitleBar titleBar) {
        super.onTitleBarCreated(titleBar);
        titleBar.setLeftVisibility(View.GONE);
        titleBar.setText(R.string.app_name);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onLayoutCreated(Bundle savedInstanceState) {
        findViewWithId(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading("加载中...");
            }
        });
    }
}
