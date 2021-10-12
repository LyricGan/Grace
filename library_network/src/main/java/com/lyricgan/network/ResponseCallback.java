package com.lyricgan.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 网络请求回调接口
 * @author Lyric Gan
 */
public interface ResponseCallback extends Callback {

    void onFailure(Call call, IOException e);

    void onResponse(Call call, Response response) throws IOException;
}
