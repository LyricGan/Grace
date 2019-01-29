package com.lyric.arch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * base title bar
 *
 * @author lyricgan
 */
public class AppTitleBar {
    private View mTitleView;
    private TextView tvLeftText, tvCenterText, tvRightText, tvRightSecondText;
    private ImageView ivLeftImage, ivCenterImage, ivRightImage, ivRightSecondImage;

    public AppTitleBar(View titleView) {
        this.mTitleView = titleView;

        bindViews(titleView);
    }

    public View getTitleView() {
        return mTitleView;
    }

    public void setTitleView(View titleView) {
        this.mTitleView = titleView;
        bindViews(titleView);
    }

    private void bindViews(View titleView) {
        if (titleView == null) {
            return;
        }
        tvLeftText = titleView.findViewById(R.id.title_bar_left_text);
        ivLeftImage = titleView.findViewById(R.id.title_bar_left_image);
        tvCenterText = titleView.findViewById(R.id.title_bar_center_text);
        ivCenterImage = titleView.findViewById(R.id.title_bar_center_image);
        tvRightText = titleView.findViewById(R.id.title_bar_right_text);
        tvRightSecondText = titleView.findViewById(R.id.title_bar_right_second_text);
        ivRightImage = titleView.findViewById(R.id.title_bar_right_image);
        ivRightSecondImage = titleView.findViewById(R.id.title_bar_right_second_image);
    }

    public void setLeftText(int resId) {
        if (tvLeftText == null) {
            return;
        }
        tvLeftText.setText(resId);
    }

    public void setLeftText(CharSequence text) {
        if (tvLeftText == null) {
            return;
        }
        tvLeftText.setText(text);
    }

    public void setLeftImage(int resId) {
        if (ivLeftImage == null) {
            return;
        }
        ivLeftImage.setImageResource(resId);
    }

    public void setCenterText(int resId) {
        if (tvCenterText == null) {
            return;
        }
        tvCenterText.setText(resId);
    }

    public void setCenterText(CharSequence text) {
        if (tvCenterText == null) {
            return;
        }
        tvCenterText.setText(text);
    }

    public void setCenterImage(int redId) {
        if (ivCenterImage == null) {
            return;
        }
        ivCenterImage.setImageResource(redId);
    }

    public void setRightText(int resId) {
        if (tvRightText == null) {
            return;
        }
        tvRightText.setText(resId);
    }

    public void setRightText(CharSequence text) {
        if (tvRightText == null) {
            return;
        }
        tvRightText.setText(text);
    }

    public void setRightSecondText(int resId) {
        if (tvRightSecondText == null) {
            return;
        }
        tvRightSecondText.setText(resId);
    }

    public void setRightSecondText(CharSequence text) {
        if (tvRightSecondText == null) {
            return;
        }
        tvRightSecondText.setText(text);
    }

    public void setRightImage(int resId) {
        if (ivRightImage == null) {
            return;
        }
        ivRightImage.setImageResource(resId);
    }

    public void setRightSecondImage(int resId) {
        if (ivRightSecondImage == null) {
            return;
        }
        ivRightSecondImage.setImageResource(resId);
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
