package com.lyric.grace.samples;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.lyric.grace.R;
import com.lyric.grace.samples.app.BaseActivity;
import com.lyricgan.grace.widget.adapter.GraceFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private TextView tvCurrentPage, tvTotalPage;

    @Override
    public int getContentViewId() {
        return R.layout.main_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        viewPager = findViewById(R.id.view_pager);
        tvCurrentPage = findViewById(R.id.tv_current_page);
        tvTotalPage = findViewById(R.id.tv_total_page);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        fragments.add(MainFragment.newInstance());
        titles.add(MainFragment.class.getSimpleName());

        fragments.add(NestedScrollFragment.newInstance());
        titles.add(NestedScrollFragment.class.getSimpleName());

        fragments.add(RefreshFragment.newInstance());
        titles.add(RefreshFragment.class.getSimpleName());

        GraceFragmentPagerAdapter adapter = new GraceFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);

        int adapterCount = adapter.getCount();
        viewPager.setOffscreenPageLimit(adapterCount - 1);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updatePagerIndicator(position);
            }
        });
        updatePagerIndicator(0);
        String totalPage = "/" + adapterCount;
        tvTotalPage.setText(totalPage);
    }

    private void updatePagerIndicator(int currentPage) {
        tvCurrentPage.setText(String.valueOf(currentPage + 1));
    }
}
