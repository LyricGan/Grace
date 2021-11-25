package com.lyricgan.grace.samples.widget;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * 适配器基类
 * @author Lyric Gan
 */
public abstract class AbsPagerAdapter<T> extends PagerAdapter {
    private final List<T> mItems;
    private final List<View> mViews;
    private final List<String> mTitles;

    public AbsPagerAdapter(List<T> items, List<View> views, List<String> titles) {
        this.mItems = items;
        this.mViews = views;
        this.mTitles = titles;
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mViews.get(position);
        T item = mItems.get(position);
        if (item != null) {
            instantiateItem(itemView, container, item, position);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViews.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && position >= 0 && position < mTitles.size()) {
            return mTitles.get(position);
        }
        return super.getPageTitle(position);
    }

    protected abstract void instantiateItem(View itemView, ViewGroup container, T item, int position);
}
