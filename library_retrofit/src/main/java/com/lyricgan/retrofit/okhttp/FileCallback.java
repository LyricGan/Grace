package com.lyricgan.retrofit.okhttp;

public interface FileCallback {

    void onProgress(long contentLength, long currentBytes, boolean isFinished);
}
