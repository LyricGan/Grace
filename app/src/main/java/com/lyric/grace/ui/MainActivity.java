package com.lyric.grace.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.lyric.grace.GraceApplication;
import com.lyric.grace.R;
import com.lyric.grace.common.BaseActivity;
import com.lyric.grace.common.BaseFragment;
import com.lyric.grace.common.BaseFragmentPagerAdapter;
import com.lyric.grace.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用主页面
 * @author lyricgan
 * @date 2016/9/1 15:47
 */
public class MainActivity extends BaseActivity {
    private ViewPager mViewPager;

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
        mViewPager = findViewWithId(R.id.view_pager);

        init();
    }

    private void init() {
        List<Fragment> fragments = new ArrayList<>();
        MainTabFragment tabFragment = new MainTabFragment();
        fragments.add(tabFragment);
        tabFragment = new MainTabFragment();
        fragments.add(tabFragment);
        tabFragment = new MainTabFragment();
        fragments.add(tabFragment);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(GraceApplication.getApplication(), "第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        BaseFragmentPagerAdapter tabPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(tabPagerAdapter);
    }

    public static class MainTabFragment extends BaseFragment {

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
}
