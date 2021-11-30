package com.lyricgan.grace.samples.widget;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyricgan.grace.samples.R;

/**
 * 自定义标题栏
 */
public class TitleBar {
    private final View mView;
    private ViewGroup titleBarView;
    private TextView tvLeftText, tvCenterText, tvRightText, tvRightSecondText;
    private ImageView ivLeftImage, ivCenterImage, ivRightImage, ivRightSecondImage;

    public TitleBar(View view) {
        this.mView = view;
    }

    public void bindViews() {
        View view = mView;
        if (view == null) {
            return;
        }
        titleBarView = view.findViewById(R.id.title_bar);
        tvLeftText = view.findViewById(R.id.title_bar_left_text);
        ivLeftImage = view.findViewById(R.id.title_bar_left_image);
        tvCenterText = view.findViewById(R.id.title_bar_center_text);
        ivCenterImage = view.findViewById(R.id.title_bar_center_image);
        tvRightText = view.findViewById(R.id.title_bar_right_text);
        tvRightSecondText = view.findViewById(R.id.title_bar_right_second_text);
        ivRightImage = view.findViewById(R.id.title_bar_right_image);
        ivRightSecondImage = view.findViewById(R.id.title_bar_right_second_image);
    }

    public void setBackgroundColor(int color) {
        if (titleBarView != null) {
            titleBarView.setBackgroundColor(color);
        }
    }

    public void setBackgroundResource(int resId) {
        if (titleBarView != null) {
            titleBarView.setBackgroundResource(resId);
        }
    }

    public void setBackground(Drawable background) {
        if (titleBarView != null) {
            titleBarView.setBackground(background);
        }
    }

    public void setLeftText(int resId) {
        if (tvLeftText != null) {
            tvLeftText.setText(resId);
        }
    }

    public void setLeftText(CharSequence text) {
        if (tvLeftText != null) {
            tvLeftText.setText(text);
        }
    }

    public void setLeftImage(int resId) {
        if (ivLeftImage != null) {
            ivLeftImage.setImageResource(resId);
        }
    }

    public void setCenterText(int resId) {
        if (tvCenterText != null) {
            tvCenterText.setText(resId);
        }
    }

    public void setCenterText(CharSequence text) {
        if (tvCenterText != null) {
            tvCenterText.setText(text);
        }
    }

    public void setCenterImage(int redId) {
        if (ivCenterImage != null) {
            ivCenterImage.setImageResource(redId);
        }
    }

    public void setRightText(int resId) {
        if (tvRightText != null) {
            tvRightText.setText(resId);
        }
    }

    public void setRightText(CharSequence text) {
        if (tvRightText != null) {
            tvRightText.setText(text);
        }
    }

    public void setRightSecondText(int resId) {
        if (tvRightSecondText != null) {
            tvRightSecondText.setText(resId);
        }
    }

    public void setRightSecondText(CharSequence text) {
        if (tvRightSecondText != null) {
            tvRightSecondText.setText(text);
        }
    }

    public void setRightImage(int resId) {
        if (ivRightImage != null) {
            ivRightImage.setImageResource(resId);
        }
    }

    public void setRightSecondImage(int resId) {
        if (ivRightSecondImage != null) {
            ivRightSecondImage.setImageResource(resId);
        }
    }

    public void setLeftTextOnClickListener(View.OnClickListener listener) {
        if (tvLeftText != null) {
            tvLeftText.setOnClickListener(listener);
        }
    }

    public void setLeftImageOnClickListener(View.OnClickListener listener) {
        if (ivLeftImage != null) {
            ivLeftImage.setOnClickListener(listener);
        }
    }

    public void setCenterTextOnClickListener(View.OnClickListener listener) {
        if (tvCenterText != null) {
            tvCenterText.setOnClickListener(listener);
        }
    }

    public void setCenterImageOnClickListener(View.OnClickListener listener) {
        if (ivCenterImage != null) {
            ivCenterImage.setOnClickListener(listener);
        }
    }

    public void setRightTextOnClickListener(View.OnClickListener listener) {
        if (tvRightText != null) {
            tvRightText.setOnClickListener(listener);
        }
    }

    public void setRightImageOnClickListener(View.OnClickListener listener) {
        if (ivRightImage != null) {
            ivRightImage.setOnClickListener(listener);
        }
    }

    public void setRightSecondTextOnClickListener(View.OnClickListener listener) {
        if (tvRightSecondText != null) {
            tvRightSecondText.setOnClickListener(listener);
        }
    }

    public void setRightSecondImageOnClickListener(View.OnClickListener listener) {
        if (ivRightSecondImage != null) {
            ivRightSecondImage.setOnClickListener(listener);
        }
    }
}
