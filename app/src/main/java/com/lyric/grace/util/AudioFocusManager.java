package com.lyric.grace.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

/**
 * @author lyricgan
 * @since 2019/2/28
 */
public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener {
    private AudioManager mAudioManager;
    private AudioFocusRequest mFocusRequest;
    private final Object focusLock = new Object();
    private boolean playbackDelayed = false;
    private boolean resumeOnFocusGain = false;
    private OnAudioFocusListener mAudioFocusListener;

    public interface OnAudioFocusListener {

        void onFocusGain();

        void onFocusFailed();

        void onFocusDelayed();
    }

    public AudioFocusManager(Context context) {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(audioAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(this, new Handler(Looper.getMainLooper()))
                    .build();
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (playbackDelayed || resumeOnFocusGain) {
                    synchronized(focusLock) {
                        playbackDelayed = false;
                        resumeOnFocusGain = false;
                    }
                    if (mAudioFocusListener != null) {
                        mAudioFocusListener.onFocusGain();
                    }
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                synchronized(focusLock) {
                    resumeOnFocusGain = false;
                    playbackDelayed = false;
                }
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onFocusFailed();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                synchronized(focusLock) {
                    resumeOnFocusGain = true;
                    playbackDelayed = false;
                }
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onFocusFailed();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onFocusFailed();
                }
                break;
        }
    }

    public void requestAudioFocus() {
        if (mAudioManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int result = mAudioManager.requestAudioFocus(mFocusRequest);
            synchronized (focusLock) {
                setRequestResult(result);
            }
        } else {
            int result = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            setRequestResult(result);
        }
    }

    public void abandonAudioFocus() {
        if (mAudioManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mAudioManager.abandonAudioFocusRequest(mFocusRequest);
        } else {
            mAudioManager.abandonAudioFocus(this);
        }
    }

    public void setAudioFocusListener(OnAudioFocusListener listener) {
        this.mAudioFocusListener = listener;
    }

    private void setRequestResult(int result) {
        switch (result) {
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onFocusGain();
                }
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onFocusFailed();
                }
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_DELAYED:
                playbackDelayed = true;
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onFocusDelayed();
                }
                break;
        }
    }
}
