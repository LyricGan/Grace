package com.lyric.support.net.okhttp;

/**
 * 文件进度回调接口
 * @author lyricgan
 */
public interface FileCallback {

    void onProgress(long contentLength, long currentBytes, boolean isFinished);
}
