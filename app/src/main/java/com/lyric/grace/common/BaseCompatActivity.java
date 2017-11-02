package com.lyric.grace.common;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lyric.grace.R;
import com.lyric.grace.widget.TitleBar;

/**
 * 带自定义标题栏的基类activity
 * @author lyricgan
 * @time 2016/5/26 13:59
 */
public abstract class BaseCompatActivity extends BaseActivity {
    private TitleBar mTitleBar;

    @Override
    public void onPrepareCreate(Bundle savedInstanceState) {
        super.onPrepareCreate(savedInstanceState);
        if (isUseTitleBar()) {
            mTitleBar = new TitleBar(this);
            mTitleBar.setLeftDrawable(R.drawable.icon_back);
            mTitleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTitleBarLeftClick();
                }
            });
            onTitleBarCreated(mTitleBar);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        if (mTitleBar == null) {
            super.setContentView(view);
            return;
        }
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(mTitleBar, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        super.setContentView(layout);
    }

    protected boolean isUseTitleBar() {
        return true;
    }

    protected void onTitleBarCreated(TitleBar titleBar) {
    }

    protected TitleBar getTitleBar() {
        return mTitleBar;
    }

    protected void onTitleBarLeftClick() {
        super.onBackPressed();
    }
}
