package com.lyric.grace.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyric.grace.R;

/**
 * 自定义标题栏
 * @author lyricgan
 * @time 2016/1/20 14:21
 */
public class TitleBar extends FrameLayout {
    private ImageView ivTitleBarLeft;
    private TextView tvTitle;
    private TextView tvTitleBarRight;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        View rootView = View.inflate(context, R.layout.view_title_bar, this);
        ivTitleBarLeft = (ImageView) rootView.findViewById(R.id.iv_title_bar_left);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitleBarRight = (TextView) rootView.findViewById(R.id.tv_title_bar_right);

        ivTitleBarLeft.setVisibility(View.GONE);
        tvTitleBarRight.setVisibility(View.GONE);
    }

    public void setText(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setText(int textId) {
        tvTitle.setText(textId);
    }

    public void setLeftDrawable(int resId) {
        ivTitleBarLeft.setImageResource(resId);
        ivTitleBarLeft.setVisibility(View.VISIBLE);
    }

    public void setLeftClickListener(OnClickListener listener) {
        ivTitleBarLeft.setOnClickListener(listener);
    }

    public void setLeftVisibility(int visibility) {
        ivTitleBarLeft.setVisibility(visibility);
    }

    public void setRightText(CharSequence text) {
        tvTitleBarRight.setText(text);
        tvTitleBarRight.setVisibility(View.VISIBLE);
    }

    public void setRightText(int textId) {
        tvTitleBarRight.setText(textId);
        tvTitleBarRight.setVisibility(View.VISIBLE);
    }

    public void setRightClickListener(OnClickListener listener) {
        tvTitleBarRight.setOnClickListener(listener);
    }

    public void setRightVisibility(int visibility) {
        tvTitleBarRight.setVisibility(visibility);
    }
}
