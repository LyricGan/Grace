package com.lyric.grace.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.lyric.grace.base.BaseCompatActivity;
import com.lyric.grace.view.TitleBar;
import com.lyric.grace.widget.MovedCircleView;

/**
 * @author lyric
 * @description
 * @time 2016/3/15 15:12
 */
public class ViewTestActivity extends BaseCompatActivity {

    @Override
    public void onViewCreate(Bundle savedInstanceState) {
        LinearLayout rootLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setLayoutParams(params);

        MovedCircleView movedCircleView = new MovedCircleView(this);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        viewParams.gravity = Gravity.CENTER;
        movedCircleView.setLayoutParams(viewParams);
        rootLayout.addView(movedCircleView);

        setContentView(rootLayout);
    }

    @Override
    public void onTitleCreated(TitleBar titleBar) {
        titleBar.setText("ViewTest");
    }
}
