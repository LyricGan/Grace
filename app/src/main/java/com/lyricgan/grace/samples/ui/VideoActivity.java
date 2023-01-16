package com.lyricgan.grace.samples.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyricgan.grace.samples.R;
import com.lyricgan.grace.samples.app.BaseActivity;
import com.lyricgan.media.video.VideoLayout;
import com.lyricgan.media.video.model.MediaPlayMode;

/**
 * 视频播放页
 * @author Lyric Gan
 */
public class VideoActivity extends BaseActivity {
    private VideoLayout videoLayout;

    @Override
    public int getContentViewId() {
        return R.layout.video_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("key_url");
        videoLayout = findViewById(R.id.video_layout);

        videoLayout.setPlayerViewCallback(new VideoLayout.PlayerViewCallback() {
            @Override
            public void onPrepared() {
            }

            @Override
            public void onPlayModeChanged(int playMode) {
            }

            @Override
            public void onFinish(int playMode) {
                if (playMode == MediaPlayMode.FULLSCREEN) {
                    videoLayout.requestPlayMode(MediaPlayMode.WINDOW);
                } else if (playMode == MediaPlayMode.WINDOW) {
                    onBackPressed();
                }
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
            }
        });
        videoLayout.play(videoUrl);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoLayout.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoLayout.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoLayout.onDestroy();
    }
}
