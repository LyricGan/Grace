package com.lyric.okhttp;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * 字符串请求回调接口
 * @author lyricgan
 * @date 2017/12/28 11:29
 */
public abstract class OnStringCallback extends HttpResponseCallback<String> {

    @Override
    public String parseResponse(ResponseBody responseBody) {
        try {
            return responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
