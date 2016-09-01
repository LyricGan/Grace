package com.lyric.grace.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lyric.grace.library.utils.ActivityUtils;
import com.lyric.grace.library.utils.HandlerUtils;

/**
 * @author lyricgan
 * @description start page
 * @time 2016/9/1 14:28
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HandlerUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.toActivity(SplashActivity.this, HomeActivity.class);
                finish();
            }
        }, 1000);
    }
}
