package com.lyricgan.test.okhttp;

public interface FileCallback {

    void onProgress(long contentLength, long currentBytes, boolean isFinished);
}
