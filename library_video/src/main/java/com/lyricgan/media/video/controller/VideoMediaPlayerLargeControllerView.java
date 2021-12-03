package com.lyricgan.media.video.controller;

import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lyricgan.media.video.R;
import com.lyricgan.media.video.model.MediaPlayMode;
import com.lyricgan.media.video.model.MediaPlayerVideoQuality;
import com.lyricgan.media.video.model.MediaQualityBean;
import com.lyricgan.media.video.model.RelateVideoInfo;
import com.lyricgan.media.video.ui.MediaPlayerControllerBrightView;
import com.lyricgan.media.video.ui.MediaPlayerControllerVolumeView;
import com.lyricgan.media.video.ui.MediaPlayerLockView;
import com.lyricgan.media.video.ui.MediaPlayerQualityPopupView;
import com.lyricgan.media.video.ui.MediaPlayerSeekView;
import com.lyricgan.media.video.ui.MediaPlayerVideoSeekBar;
import com.lyricgan.media.video.ui.MediaPlayerVolumeSeekBar;
import com.lyricgan.media.video.util.MediaPlayerUtils;

import java.util.List;

/**
 * 横屏控制页面
 */
public class VideoMediaPlayerLargeControllerView extends MediaPlayerBaseControllerView implements View.OnClickListener, MediaPlayerVolumeSeekBar.OnScreenShowListener, OnSystemUiVisibilityChangeListener {
    protected static final String TAG = VideoMediaPlayerLargeControllerView.class.getSimpleName();
    private RelativeLayout mControllerTopView;
    private RelativeLayout mBackLayout;
    private TextView mTitleTextView;

    private ImageView mVideoPlayImageView; // 播放暂停

    private RelativeLayout mVideoProgressLayout;
    //快进快退
    private MediaPlayerVideoSeekBar mSeekBar;
    private TextView mCurrentTimeTextView; // 当前时间
    private TextView mTotalTimeTextView; // 总时间
    private ImageView mScreenModeImageView;

    private MediaPlayerQualityPopupView mQualityPopup; // 清晰度

    private MediaPlayerLockView mLockView; // 锁屏
    private TextView video_top_switch_parts;
    private TextView video_top_rate;
    private ImageView video_volume_ic;
    protected MediaPlayerControllerBrightView mControllerBrightView;
    protected MediaPlayerControllerVolumeView mWidgetVolumeControl;
    protected MediaPlayerSeekView mWidgetSeekView;
    private boolean isFirst;

    public VideoMediaPlayerLargeControllerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VideoMediaPlayerLargeControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoMediaPlayerLargeControllerView(Context context) {
        super(context);
        mLayoutInflater.inflate(R.layout.video_blue_media_player_controller_large, this);

        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        mControllerTopView = findViewById(R.id.controller_top_layout);
        mBackLayout = findViewById(R.id.back_layout); // 返回
        mTitleTextView = findViewById(R.id.title_text_view);

        mVideoPlayImageView = findViewById(R.id.video_start_pause_image_view); // 播放控制

        video_top_switch_parts = findViewById(R.id.video_top_switch_parts);
        video_top_rate = findViewById(R.id.video_top_rate);
        video_volume_ic = findViewById(R.id.video_volume_ic);

        mLockView = findViewById(R.id.widget_lock_view);
        mVideoProgressLayout = findViewById(R.id.video_progress_layout);
        mSeekBar = findViewById(R.id.video_seekbar);
        mCurrentTimeTextView = findViewById(R.id.video_current_time_text_view);
        mTotalTimeTextView = findViewById(R.id.video_total_time_text_view);
        mScreenModeImageView = findViewById(R.id.video_window_screen_image_view); // 大屏切小屏

        mQualityPopup = new MediaPlayerQualityPopupView(getContext());

        mControllerBrightView = findViewById(R.id.widge_control_light_view); // 新亮度调节
        mWidgetVolumeControl = findViewById(R.id.widget_controller_volume);

        mWidgetSeekView = findViewById(R.id.widget_seek_view);

        setOnSystemUiVisibilityChangeListener(this);
    }

    @Override
    public void initListeners() {
        mScreenModeImageView.setOnClickListener(this);
        video_top_switch_parts.setOnClickListener(this);
        video_top_rate.setOnClickListener(this);
        mBackLayout.setOnClickListener(this);
        mVideoPlayImageView.setOnClickListener(this);
        mTitleTextView.setOnClickListener(this);
        video_volume_ic.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        //清晰度
        mQualityPopup.setCallback(new MediaPlayerQualityPopupView.Callback() {
            @Override
            public void onQualitySelected(MediaQualityBean quality) {
                mQualityPopup.hide();
                //                video_top_rate.setText(quality.getQualityName());
                if (!quality.getQualityName().equals(mCurrentQuality.getQualityName())) {
                    video_top_rate.setSelected(true);
                    setMediaQuality(quality);
                }
            }

            @Override
            public void onPopupViewDismiss() {
                video_top_rate.setSelected(false);
                if (isShowing()) {
                    show();
                }
            }
        });

        mLockView.setCallback(new MediaPlayerLockView.ScreenLockCallback() {

            @Override
            public void onActionLockMode(boolean lock) {// 加锁或解锁
                mScreenLock = lock;
                ((IVideoController) mMediaPlayerController).onRequestLockMode(lock);
                show();
            }
        });
    }

    @Override
    public void setMediaQuality(MediaQualityBean quality) {
    }

    @Override
    public void setRelateVideoInfo(RelateVideoInfo relateVideoInfo) {
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
        ((IVideoController) mMediaPlayerController).onControllerShow(MediaPlayMode.FULLSCREEN);
        if (mScreenLock) {
            mControllerTopView.setVisibility(INVISIBLE);
            mVideoProgressLayout.setVisibility(INVISIBLE);
            mWidgetVolumeControl.setVisibility(INVISIBLE);
            mControllerBrightView.setVisibility(INVISIBLE);
        } else {
            mControllerTopView.setVisibility(VISIBLE);
            mVideoProgressLayout.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onHide() {
        ((IVideoController) mMediaPlayerController).onControllerHide(MediaPlayMode.FULLSCREEN);
        mControllerTopView.setVisibility(INVISIBLE);
        mVideoProgressLayout.setVisibility(INVISIBLE);
        mWidgetVolumeControl.setVisibility(INVISIBLE);
        mControllerBrightView.setVisibility(INVISIBLE);

        if (mQualityPopup.isShowing()) {
            mQualityPopup.hide();
        }
        // 当前全屏模式,隐藏系统UI
        if (mDeviceNavigationBarExist) {
            if (MediaPlayerUtils.isFullScreenMode(((IVideoController) mMediaPlayerController).getPlayMode())) {
                MediaPlayerUtils.hideSystemUI(mHostWindow, false);
            }
        }
        mLockView.hide();
    }

    public void updateVideoTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
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

    public void updateVideoPlaybackState(boolean isStart) {
        if (isStart) {
            mVideoPlayImageView.setImageResource(R.drawable.video_pause_land_image);
        } else {
            mVideoPlayImageView.setImageResource(R.drawable.video_play_land_image);
        }
    }

    public void updateVideoQualityState(MediaPlayerVideoQuality quality) {
    }

    public void updateVideoVolumeState() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBackLayout.getId() || id == mTitleTextView.getId()) {// 返回
            ((IVideoController) mMediaPlayerController).onBackPressed(MediaPlayMode.FULLSCREEN);

        } else if (id == mVideoPlayImageView.getId()) {// 播放暂停
            Log.i(TAG, "playing  ? " + (mMediaPlayerController.isPlaying()));
            if (mMediaPlayerController.isPlaying()) {
                mMediaPlayerController.pause();
                if (mScreenLock) {
                    show();
                } else {
                    show(0);
                }
            } else if (!mMediaPlayerController.isPlaying()) {
                mMediaPlayerController.start();
                show();
            }

        } else if (id == mScreenModeImageView.getId()) { // 切换大小屏幕
            ((IVideoController) mMediaPlayerController).onRequestPlayMode(MediaPlayMode.WINDOW);
        } else if (id == video_top_rate.getId()) {//清晰度
            displayQualityPopupWindow();
        } else if (id == video_volume_ic.getId()) {//声音
            if (mWidgetVolumeControl.getVisibility() == VISIBLE) {
                mWidgetVolumeControl.setVisibility(GONE);
            } else {
                mWidgetVolumeControl.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void onShowVolumeControl() {
        mWidgetVolumeControl.setVisibility(VISIBLE);
    }

    /**
     * 清晰度的弹框
     */
    private void displayQualityPopupWindow() {
    }

    @Override
    public void onScreenShow() {
        show();
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        Log.d(TAG, "onSystemUiVisibilityChange :" + visibility);
    }

    @Override
    public void onWindowSystemUiVisibilityChanged(int visible) {
        Log.d(TAG, "onWindowSystemUiVisibilityChanged :" + visible);
    }

    protected void onHideSeekView() {
        if (mWidgetSeekView != null && mWidgetSeekView.isShowing()) {
            mWidgetSeekView.hide(true);
        }
    }

    protected void onGestureSeekBegin(int currentPosition, int duration) {
        mWidgetSeekView.onGestureSeekBegin(currentPosition, duration);
    }

    protected void onGestureVolumeChange(float distanceY, float totalVolumeDistance, AudioManager audioManager) {
        if (mWidgetVolumeControl != null && mWidgetVolumeControl.getVisibility() == VISIBLE) {
            mWidgetVolumeControl.onGestureVolumeChange(distanceY, totalVolumeDistance, audioManager);
        }
    }

    protected void onGestureLightChange(float distanceY, Window mHostWindow) {
        if (mControllerBrightView != null && mControllerBrightView.getVisibility() == VISIBLE) {
            mControllerBrightView.onGestureLightChange(distanceY, mHostWindow);
        }
    }

    protected void onGestureSeekChange(float distanceY, float totalSeekDistance) {
        if (mWidgetSeekView != null) {
            mWidgetSeekView.onGestureSeekChange(distanceY, totalSeekDistance);
            if (mediaPlayerEventActionView != null) {
                if (mediaPlayerEventActionView.isShowing()) {
                    mediaPlayerEventActionView.hideCompleteLayout();
                }
            }
        }
    }

    protected void onSeekTo() {
        if (mWidgetSeekView != null) {
            long seekPosition = mWidgetSeekView.onGestureSeekFinish();
            if (seekPosition >= 0 && seekPosition <= mMediaPlayerController.getDuration()) {
                mMediaPlayerController.seekTo((int) seekPosition);
                // mMediaPlayerController.start();
            }
        }
    }

    protected void onShowHide() {
        if (mWidgetSeekView != null) {
            mWidgetSeekView.hide(true);
        }
    }

    /**
     * 设置清晰度
     * @param qualityBeanList 清晰度列表
     * @param currentQuality 当前清晰度
     */
    public void setQuality(List<MediaQualityBean> qualityBeanList, MediaQualityBean currentQuality) {
        this.mCurrentQuality = currentQuality;

        video_top_rate.setText(currentQuality.getQualityName());
        video_top_rate.setSelected(true);
        if (qualityBeanList == null || qualityBeanList.size() <= 1) {
            video_top_rate.setEnabled(false);
            video_top_rate.setTextColor(getResources().getColor(R.color.gray_primary_dark));
        } else {
            video_top_rate.setTextColor(getResources().getColor(R.color.player_quality_text_selector));
            video_top_rate.setEnabled(true);
        }
    }

    public void setRelateVideo(List<RelateVideoInfo> lst, RelateVideoInfo relateVideoInfo) {
    }

    @Override
    public void show() {
        super.show();
        // 已通过广播实现音量变化监听，每次显示更新音量控件
        mWidgetVolumeControl.updateVolumeSeekBar();
    }
}
