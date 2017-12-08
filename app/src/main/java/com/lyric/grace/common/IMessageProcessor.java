package com.lyric.grace.common;

import android.os.Handler;
import android.os.Message;

/**
 * 消息事件处理器
 * @author lyricgan
 * @date 2017/11/3 9:56
 */
public interface IMessageProcessor {

    /**
     * 处理消息回调，例如：getHandler().sendEmptyMessage(0);
     * @param msg 消息实体
     */
    void handleMessage(Message msg);

    /**
     * 获取消息处理器
     * @return 消息处理器
     */
    Handler getHandler();
}
