package com.lyric.grace.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.lyric.grace.R;
import com.lyric.grace.common.BaseFragment;

/**
 * 主页面tab
 * @author lyricgan
 * @date 17/11/5 下午5:18
 */
public class MainTabFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_tab;
    }

    @Override
    public void onLayoutCreated(Bundle savedInstanceState) {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
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
