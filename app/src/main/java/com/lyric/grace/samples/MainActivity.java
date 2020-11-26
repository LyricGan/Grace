package com.lyric.grace.samples;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.lyric.grace.R;
import com.lyricgan.grace.widget.adapter.GraceFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private TextView tvCurrentPage, tvTotalPage;
    private RelativeLayout relativePoint;
    private LinearLayout linearPoint;
    private View viewFocusPoint;
    private ViewPager viewPager;

    private int mPointPageMargin;

    @Override
    public int getContentViewId() {
        return R.layout.main_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        tvCurrentPage = findViewById(R.id.tv_current_page);
        tvTotalPage = findViewById(R.id.tv_total_page);
        relativePoint = findViewById(R.id.relative_point);
        linearPoint = findViewById(R.id.linear_point);
        viewFocusPoint = findViewById(R.id.view_focus_point);
        viewPager = findViewById(R.id.view_pager);

        viewPager.setPageMargin(20);
        viewPager.setPageMarginDrawable(new ColorDrawable(Color.BLACK));
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
        final int size = 3;
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fragments.add(MainFragment.newInstance());
            titles.add(String.valueOf(i + 1));
        }
        updatePagerIndicator(0);
        String totalPage = "/" + size;
        tvTotalPage.setText(totalPage);

        relativePoint.setVisibility(View.VISIBLE);
        int itemSize = 8;
        for (int i = 0; i < size; i++) {
            View childView = new View(this);
            childView.setBackgroundResource(R.drawable.circle_white);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemSize, itemSize);
            if (i > 0) {
                params.leftMargin = itemSize;
            }
            childView.setLayoutParams(params);
            linearPoint.addView(childView);
        }
        linearPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (linearPoint.getChildCount() > 1) {
                    mPointPageMargin = linearPoint.getChildAt(1).getLeft() - linearPoint.getChildAt(0).getLeft();
                }
            }
        });
        GraceFragmentPagerAdapter adapter = new GraceFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(size);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mPointPageMargin > 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewFocusPoint.getLayoutParams();
                    params.leftMargin = (int) (mPointPageMargin * positionOffset) + mPointPageMargin * position;
                    viewFocusPoint.setLayoutParams(params);
                }
            }

            @Override
            public void onPageSelected(int position) {
                updatePagerIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void updatePagerIndicator(int currentPage) {
        tvCurrentPage.setText(String.valueOf(currentPage + 1));
    }
}
