package com.lyricgan.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class GracePagerAdapter<T> extends PagerAdapter {
    private Context mContext;
    private List<T> mItems;
    private List<View> mViews;
    private List<String> mTitles;

    public GracePagerAdapter(Context context, List<T> items, int layoutId) {
        this(context, items, layoutId, null);
    }

    public GracePagerAdapter(Context context, List<T> items, int layoutId, List<String> titles) {
        this.mContext = context;
        this.mItems = items;
        this.mViews = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            mViews.add(LayoutInflater.from(context).inflate(layoutId, null, false));
        }
        this.mTitles = titles;
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
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
        T item = mItems.get(position);
        if (item != null) {
            instantiateItem(itemView, container, item, position);
        }
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
        return super.getPageTitle(position);
    }

    protected abstract void instantiateItem(View itemView, ViewGroup container, T item, int position);

    public Context getContext() {
        return mContext;
    }
}
