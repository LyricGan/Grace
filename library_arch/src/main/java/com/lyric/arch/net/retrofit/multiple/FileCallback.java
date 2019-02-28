package com.lyric.arch.net.retrofit.multiple;

/**
 * file callback with upload and download
 *
 * @author lyricgan
 */
public interface FileCallback {

    void onProgress(long currentSize, long totalSize, boolean isCompleted);
}
