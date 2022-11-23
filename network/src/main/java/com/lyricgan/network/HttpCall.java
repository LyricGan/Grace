package com.lyricgan.network;

import okhttp3.Call;

/**
 * 网络请求回调
 * @author Lyric Gan
 */
public class HttpCall {
    private final Call call;

    public HttpCall(Call call) {
        this.call = call;
    }

    public Call getCall() {
        return call;
    }
}
