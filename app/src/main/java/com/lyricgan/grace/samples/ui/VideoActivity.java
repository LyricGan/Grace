package com.lyricgan.grace.samples.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyricgan.grace.samples.R;
import com.lyricgan.grace.samples.app.BaseActivity;
import com.lyricgan.grace.samples.constants.IExtras;
import com.lyricgan.media.video.VideoPlayerView;
import com.lyricgan.media.video.model.MediaPlayMode;

/**
 * 视频播放页
 * @author Lyric Gan
 */
public class VideoActivity extends BaseActivity {
    private VideoPlayerView videoPlayerView;

    @Override
    public int getContentViewId() {
        return R.layout.video_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra(IExtras.KEY_URL);
        videoPlayerView = findViewById(R.id.video_player_view);

        videoPlayerView.setPlayerViewCallback(new VideoPlayerView.PlayerViewCallback() {
            @Override
            public void hideViews() {

            }

            @Override
            public void restoreViews() {

            }

            @Override
            public void onPrepared() {

            }

            @Override
            public void onQualityChanged(int quality) {

            }

            @Override
            public void onCourseChanged(int videoId) {

            }

            @Override
            public void onFinish(int playMode) {
                if (playMode == MediaPlayMode.FULLSCREEN) {
                    videoPlayerView.requestPlayMode(MediaPlayMode.WINDOW);
                } else if (playMode == MediaPlayMode.WINDOW) {
                    onBackPressed();
                }
            }

            @Override
            public void onError(int errorCode, String errorMsg) {

            }
        });
        videoPlayerView.play(videoUrl);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayerView.onDestroy();
    }
}
