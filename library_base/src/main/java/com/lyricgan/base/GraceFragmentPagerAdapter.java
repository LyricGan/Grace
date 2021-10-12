package com.lyricgan.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class GraceFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public GraceFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, null);
    }

    public GraceFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments != null) {
            if (position >= 0 && position < mFragments.size()) {
                return mFragments.get(position);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            if (position >= 0 && position < mTitles.size()) {
                return mTitles.get(position);
            }
        }
        return super.getPageTitle(position);
    }
}
