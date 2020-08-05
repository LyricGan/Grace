package com.lyricgan.grace.network.okhttp;

public interface FileCallback {

    void onProgress(long contentLength, long currentBytes, boolean isFinished);
}
