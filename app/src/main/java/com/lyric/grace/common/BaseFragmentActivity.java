package com.lyric.grace.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lyric.grace.R;

/**
 * @author lyricgan
 * @description
 * @time 2016/5/27 15:37
 */
public class BaseFragmentActivity extends BaseActivity {
    private static final String EXTRA_FRAGMENT_NAME = "fragment_name";

    public static Intent newIntent(Context context, String fragmentName) {
        Intent intent = new Intent(context, BaseFragmentActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_NAME, fragmentName);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_fragment;
    }

    @Override
    public void onLayoutCreated(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        String fragmentName = bundle.getString(EXTRA_FRAGMENT_NAME);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_content, Fragment.instantiate(this, fragmentName, bundle), fragmentName)
                .commit();
    }
}