package com.lyricgan.media.video.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class MediaPlayerBrightSeekBar extends SeekBar {

    public interface onScreenShowListener {
        void onScreenShow();
    }

    private onScreenShowListener mOnShowListener;

    public MediaPlayerBrightSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MediaPlayerBrightSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaPlayerBrightSeekBar(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
                if (mOnShowListener != null) {
                    mOnShowListener.onScreenShow();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    public void setOnScreenShowListener(onScreenShowListener listener) {
        this.mOnShowListener = listener;
    }
}
