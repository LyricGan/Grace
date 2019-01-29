package com.lyric.grace.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lyric.arch.AppActivity;
import com.lyric.grace.R;
import com.lyric.utils.DisplayUtils;

public class MainActivity extends AppActivity {
    private TextView tvMessage;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        tvMessage = findViewById(R.id.tv_message);
        findViewById(R.id.btn_display).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_display:
                tvMessage.setText(DisplayUtils.toDisplayString());
                break;
        }
    }
}
