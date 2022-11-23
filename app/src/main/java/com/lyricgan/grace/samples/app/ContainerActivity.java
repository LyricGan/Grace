package com.lyricgan.grace.samples.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;

import com.lyricgan.grace.samples.R;

/**
 * Fragment容器Activity
 * @author Lyric Gan
 */
public class ContainerActivity extends BaseActivity {
    private static final String KEY_NAME = "key_name";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_PARAMS = "key_params";

    private String mFragmentName;
    private String mTitle;
    private Bundle mParams;

    private Fragment mFragment;

    public static Intent newIntent(Context context, String name, String title, Bundle params) {
        Intent intent = new Intent(context, ContainerActivity.class);
        intent.putExtra(KEY_NAME, name);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_PARAMS, params);
        return intent;
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, Bundle args) {
        super.onCreateExtras(savedInstanceState, args);
        mFragmentName = args.getString(KEY_NAME);
        mTitle = args.getString(KEY_TITLE);
        mParams = args.getBundle(KEY_PARAMS);
    }

    @Override
    public int getContentViewId() {
        return R.layout.app_fragment_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        TitleBar titleBar = getTitleBar();
        if (titleBar != null) {
            titleBar.setCenterText(mTitle);
        }
        if (savedInstanceState == null) {
            initFragment(mFragmentName, mParams);
        }
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }

    @Override
    public void onBackPressed() {
        if (mFragment instanceof BaseFragment) {
            if (((BaseFragment) mFragment).onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

    protected void onInitFailed(Exception e) {
        e.printStackTrace();
        finish();
    }

    private void initFragment(String fragmentName, Bundle args) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentFactory fragmentFactory = fragmentManager.getFragmentFactory();
            Fragment fragment = fragmentFactory.instantiate(getClassLoader(), fragmentName);
            if (args != null) {
                args.setClassLoader(fragment.getClass().getClassLoader());
                fragment.setArguments(args);
            }
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_content, fragment)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();

            mFragment = fragment;
        } catch (Exception e) {
            onInitFailed(e);
        }
    }
}
