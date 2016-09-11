package com.lyric.grace.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lyric.grace.R;
import com.lyric.grace.base.BaseCompatActivity;
import com.lyric.grace.entity.HomeItemEntity;
import com.lyric.grace.library.adapter.BaseRecyclerAdapter;
import com.lyric.grace.library.utils.ActivityUtils;
import com.lyric.grace.view.TitleBar;

import java.util.ArrayList;

/**
 * @author lyricgan
 * @description MainActivity
 * @time 2016/9/1 15:47
 */
public class MainActivity extends BaseCompatActivity {
    private static final Class<?>[] ACTIVITY = { CircleProgressBarActivity.class, CollapsibleActivity.class, LoadingActivity.class, SpannableActivity.class, SwipeMenuActivity.class, SwipeMenuSimpleActivity.class, MovedViewActivity.class, WebActivity.class};
    private static final String[] TITLE = { "CircleProgressBarActivity", "CollapsibleActivity", "LoadingActivity", "SpannableActivity", "SwipeMenuActivity", "SwipeMenuSimpleActivity", "MovedViewActivity", "WebActivity"};
    private static final String[] COLOR_STR = {"#0dddb8", "#0bd4c3", "#03cdcd", "#00b1c5", "#04b2d1", "#04b2d1", "#04b2d1", "#04b2d1"};
    private ArrayList<HomeItemEntity> mDataList;
    private RecyclerView mRecyclerView;

    @Override
    public void onViewCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        initialize();
    }

    @Override
    public void onTitleCreated(TitleBar titleBar) {
        titleBar.setLeftVisibility(View.GONE);
    }

    private void initialize() {
        addData();
        BaseRecyclerAdapter<HomeItemEntity> adapter = new BaseRecyclerAdapter<HomeItemEntity>(this, R.layout.home_item_view) {
            @Override
            public void convert(View itemView, int position, HomeItemEntity item) {
                CardView cardView = (CardView) itemView.findViewById(R.id.card_view);
                TextView tvInfo = (TextView) itemView.findViewById(R.id.info_text);
                tvInfo.setText(item.getTitle());
                cardView.setCardBackgroundColor(Color.parseColor(item.getColor()));
            }
        };
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<HomeItemEntity>() {
            @Override
            public void onItemClick(int position, HomeItemEntity item, View view) {
                ActivityUtils.toActivity(MainActivity.this, (Class<? extends Activity>) item.getClazz());
            }
        });
        mRecyclerView.setAdapter(adapter);
        adapter.add(mDataList);
    }

    private void addData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItemEntity item = new HomeItemEntity();
            item.setTitle(TITLE[i]);
            item.setClazz(ACTIVITY[i]);
            item.setColor(COLOR_STR[i]);
            mDataList.add(item);
        }
    }
}
