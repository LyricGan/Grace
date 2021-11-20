package com.lyricgan.grace.samples.ui;

import android.os.Bundle;
import android.view.View;

import com.lyricgan.grace.samples.R;
import com.lyricgan.grace.samples.app.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.main_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_content, MainFragment.newInstance())
                .commitAllowingStateLoss();
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }
}
