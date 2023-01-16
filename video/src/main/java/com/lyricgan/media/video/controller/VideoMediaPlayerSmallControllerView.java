package com.lyricgan.media.video.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.lyricgan.media.video.R;
import com.lyricgan.media.video.VideoLayout;
import com.lyricgan.media.video.model.MediaPlayMode;
import com.lyricgan.media.video.ui.MediaPlayerVideoSeekBar;
import com.lyricgan.media.video.util.MediaPlayerUtils;

public class VideoMediaPlayerSmallControllerView extends MediaPlayerBaseControllerView implements View.OnClickListener {
    private RelativeLayout mControllerTopView;
    private ImageView backImage;
    private TextView mTitleTextView;

    private RelativeLayout mControllerBottomView;
    private MediaPlayerVideoSeekBar mSeekBar;
    private ImageView mPlaybackImageView;
    private ImageView mScreenModeImageView;
    private TextView mCurrentTimeTextView;
    private TextView mTotalTimeTextView;
    private ImageView overflowImage;
    private boolean isFirst;

    public void setPlayerViewTitleOptionListener(VideoLayout.PlayerViewTitleOption playerViewTitleOptionListener) {
        this.playerViewTitleOptionListener = playerViewTitleOptionListener;
    }

    VideoLayout.PlayerViewTitleOption playerViewTitleOptionListener;

    public VideoMediaPlayerSmallControllerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VideoMediaPlayerSmallControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoMediaPlayerSmallControllerView(Context context) {
        super(context);
        mLayoutInflater.inflate(R.layout.video_blue_media_player_controller_small, this);

        initViews();
        initListeners();

        mHandler.sendEmptyMessage(MSG_SHOW);
    }

    @Override
    public void initViews() {
        mControllerTopView = findViewById(R.id.controller_top_layout);
        backImage = findViewById(R.id.image_back);
        mTitleTextView = findViewById(R.id.title_text_view);
        overflowImage = findViewById(R.id.image_overflow_video);

        mControllerBottomView = findViewById(R.id.controller_bottom_layout);
        mSeekBar = findViewById(R.id.seekbar_video_progress);
        mPlaybackImageView = findViewById(R.id.video_playback_image_view);
        mScreenModeImageView = findViewById(R.id.video_fullscreen_image_view);
        mCurrentTimeTextView = findViewById(R.id.video_small_current_time_tv);
        mTotalTimeTextView = findViewById(R.id.video_small_duration_time_tv);
    }

    @Override
    public void initListeners() {
        backImage.setOnClickListener(this);
        overflowImage.setOnClickListener(this);
        mTitleTextView.setOnClickListener(this);
        mPlaybackImageView.setOnClickListener(this);
        mScreenModeImageView.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mVideoProgressTrackingTouch = false;

                int curProgress = seekBar.getProgress();
                int maxProgress = seekBar.getMax();
                if (curProgress >= 0 && curProgress <= maxProgress) {
                    float percentage = ((float) curProgress) / maxProgress;
                    int position = (int) (mMediaPlayerController.getDuration() * percentage);
                    mMediaPlayerController.seekTo(position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mVideoProgressTrackingTouch = true;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (isShowing()) {
                        show();
                    }
                }
            }
        });
    }

    @Override
    public void onTimerTicker() {
        long currentPosition = mMediaPlayerController.getCurrentPosition();
        long duration = mMediaPlayerController.getDuration();
        if (duration > 0 && currentPosition <= duration) {
            float percentage = ((float) currentPosition) / duration;
            if (percentage >= 0 && percentage <= 1) {
                int progress = (int) (percentage * mSeekBar.getMax());
                if (!mVideoProgressTrackingTouch) {
                    mSeekBar.setProgress(progress);
                }
                mCurrentTimeTextView.setText(MediaPlayerUtils.getVideoDisplayTime(currentPosition));
                mTotalTimeTextView.setText(MediaPlayerUtils.getVideoDisplayTime(duration));
            }
        }
    }

    @Override
    public void onShow() {
        mControllerTopView.setVisibility(VISIBLE);
        mControllerBottomView.setVisibility(VISIBLE);
    }

    @Override
    public void onHide() {
        mControllerTopView.setVisibility(INVISIBLE);
        mControllerBottomView.setVisibility(INVISIBLE);
    }

    public void updateVideoPlaybackState(boolean isStart) {
        // 判断是否正在播放
        if (isStart) {
            mPlaybackImageView.setImageResource(R.drawable.blue_ksy_pause);
            mPlaybackImageView.setEnabled(mMediaPlayerController.canPause());
        } else {
            mPlaybackImageView.setImageResource(R.drawable.blue_ksy_play);
            mPlaybackImageView.setEnabled(((IVideoController) mMediaPlayerController).canStart());
        }
    }

    public void updateVideoSecondProgress(int percent) {
        long duration = mMediaPlayerController.getDuration();
        long progress = duration * percent / 100;

        if (duration > 0 && !isFirst) {
            mSeekBar.setMax((int) duration);
            mSeekBar.setProgress(0);
            isFirst = true;
        }
        mSeekBar.setSecondaryProgress((int) progress);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == backImage.getId() || id == mTitleTextView.getId()) {
            ((IVideoController) mMediaPlayerController).onBackPressed(MediaPlayMode.WINDOW);
        } else if (id == mPlaybackImageView.getId()) {
            if (mMediaPlayerController.isPlaying()) {
                mMediaPlayerController.pause();
                show(0);
            } else if (!mMediaPlayerController.isPlaying()) {
                mMediaPlayerController.start();
                show();
            }
        } else if (id == mScreenModeImageView.getId()) {
            ((IVideoController) mMediaPlayerController).onRequestPlayMode(MediaPlayMode.FULLSCREEN);
        } else if (id == overflowImage.getId()) {
            onRightTopClicked();
        }
    }

    private void onRightTopClicked() {
        if (playerViewTitleOptionListener != null) {
            playerViewTitleOptionListener.onRightTopClick();
            show(0);
        }
    }
}
