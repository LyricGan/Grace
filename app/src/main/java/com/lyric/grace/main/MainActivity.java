package com.lyric.grace.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lyric.arch.AppTitleBar;
import com.lyric.arch.BaseActivity;
import com.lyric.grace.R;
import com.lyric.utils.DisplayUtils;

public class MainActivity extends BaseActivity {
    private TextView tvMessage;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState, Bundle args, AppTitleBar titleBar) {
        tvMessage = findViewById(R.id.tv_message);

        findViewById(R.id.btn_display).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_display:
                tvMessage.setText(DisplayUtils.toDisplayString());
                break;
        }
    }
}
