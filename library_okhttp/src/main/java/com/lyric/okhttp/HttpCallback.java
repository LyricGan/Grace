package com.lyric.okhttp;

import java.io.IOException;

/**
 * 网络请求回调接口
 * @author lyricgan
 */
public interface HttpCallback {

    void onResponse(HttpRequest httpRequest, HttpResponse httpResponse);

    void onFailure(HttpRequest httpRequest, IOException e);

    void onCancel(HttpRequest httpRequest);
}
