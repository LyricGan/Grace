package com.lyric.grace.activity;

import android.os.Bundle;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseCompatActivity;
import com.lyric.grace.view.TitleBar;
import com.lyric.grace.widget.webview.DefaultWebLayout;

/**
 * @author lyric
 * @description
 * @time 2016/6/23 11:38
 */
public class WebActivity extends BaseCompatActivity {
    DefaultWebLayout layout_web;

    @Override
    public void onTitleCreated(TitleBar titleBar) {
    }

    @Override
    public void onViewCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
        layout_web = (DefaultWebLayout) findViewById(R.id.layout_web);

        String testUrl = "http://www.baidu.com";

        layout_web.loadUrl(testUrl);
    }

    @Override
    public void onBackPressed() {
        if (!layout_web.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        layout_web.destroy();
    }
}
