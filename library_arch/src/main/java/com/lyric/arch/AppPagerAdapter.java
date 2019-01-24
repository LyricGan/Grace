package com.lyric.arch;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * PagerAdapter基类
 * @author lyricgan
 */
public abstract class AppPagerAdapter extends PagerAdapter {
    private List<View> mViews;
    private List<String> mTitles;

    public AppPagerAdapter(List<View> views) {
        this(views, null);
    }

    public AppPagerAdapter(List<View> views, List<String> titles) {
        this.mViews = views;
        this.mTitles = titles;
    }

    @Override
    public int getCount() {
        return mViews != null ? mViews.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViews.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mViews.get(position);
        instantiateItem(itemView, container, position);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            if (position >= 0 && position < mTitles.size()) {
                return mTitles.get(position);
            }
        }
        return null;
    }

    /**
     * 初始化Item
     * @param itemView Item视图
     * @param container 容器视图
     * @param position Item索引位置
     */
    public abstract void instantiateItem(View itemView, ViewGroup container, int position);
}
