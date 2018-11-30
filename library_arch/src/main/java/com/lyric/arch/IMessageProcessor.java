package com.lyric.arch;

import android.os.Handler;
import android.os.Message;

/**
 * 消息事件处理接口
 * @author lyricgan
 */
public interface IMessageProcessor {

    Handler getHandler();

    void handleMessage(Message msg);
}
