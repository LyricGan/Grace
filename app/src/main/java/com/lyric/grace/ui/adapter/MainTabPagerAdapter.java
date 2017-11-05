package com.lyric.grace.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lyric.grace.common.BaseFragmentPagerAdapter;

import java.util.List;

/**
 * 主页面tab适配器
 * @author lyricgan
 * @date 17/11/5 下午5:22
 */
public class MainTabPagerAdapter extends BaseFragmentPagerAdapter {

    public MainTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainTabPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, fragments);
    }
}
