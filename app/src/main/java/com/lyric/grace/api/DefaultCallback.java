package com.lyric.grace.api;

import retrofit2.Call;

/**
 * @author lyricgan
 * @description 请求响应默认回调
 * @time 2016/11/9 15:32
 */
public class DefaultCallback extends ResponseCallback {

    @Override
    public void onResponse(Call call, Object response) {
    }

    @Override
    public void onError(Call call, String errorMessage) {
    }
}
