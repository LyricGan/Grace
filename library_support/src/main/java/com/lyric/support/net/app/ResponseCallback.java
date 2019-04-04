package com.lyric.support.net.app;

/**
 * 网络请求回调接口
 * @author lyricgan
 */
public interface ResponseCallback<T> {

    void onSuccess(T response);

    void onFailed(ResponseError error);
}
