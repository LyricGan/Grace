package com.lyricgan.test.retrofit.multiple;

public interface FileCallback {

    void onProgress(long currentSize, long totalSize, boolean isCompleted);
}
