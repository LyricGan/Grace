package com.lyric.grace.common;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 基础消息处理器
 * @author lyricgan
 * @date 2017/11/3 10:03
 */
public class BaseHandler extends Handler {
    private WeakReference<IMessageProcessor> mReference;

    BaseHandler(IMessageProcessor processor) {
        mReference = new WeakReference<>(processor);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        IMessageProcessor processor = mReference.get();
        if (processor != null) {
            processor.handleMessage(msg);
        }
    }
}
