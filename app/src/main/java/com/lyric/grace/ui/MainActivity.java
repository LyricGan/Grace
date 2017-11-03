package com.lyric.grace.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.common.BaseActivity;
import com.lyric.grace.widget.TitleBar;

/**
 * 应用主页面
 * @author lyricgan
 * @date 2016/9/1 15:47
 */
public class MainActivity extends BaseActivity {

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
                getHandler().sendEmptyMessage(0);

                getHandler().sendEmptyMessageDelayed(1, 3000L);
            }
        });
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                showLoading("加载中...");
                break;
            case 1:
                hideLoading();
                break;
        }
    }
}
