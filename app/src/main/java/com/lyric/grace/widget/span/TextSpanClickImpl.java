package com.lyric.grace.widget.span;

import android.content.Context;

import com.lyric.grace.activity.SpannableTestActivity;
import com.lyric.grace.library.utils.ActivityUtils;

/**
 * @author lyric
 * @description 文本点击实现类
 * @time 2016/4/21 13:19
 */
public class TextSpanClickImpl implements ITextSpanClickListener {
    private Context mContext;
    private int mType;

    public TextSpanClickImpl(Context context, int type) {
        this.mContext = context;
        this.mType = type;
    }

    @Override
    public void onClick(int position) {
        if (1 == mType) {
            ActivityUtils.jumpActivity(mContext, SpannableTestActivity.class);
        }
    }
}
