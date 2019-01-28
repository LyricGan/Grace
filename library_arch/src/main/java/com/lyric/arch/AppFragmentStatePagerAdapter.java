package com.lyric.arch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * fragments pager adapter with state
 *
 * @author lyricgan
 */
public class AppFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public AppFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, null);
    }

    public AppFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
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
