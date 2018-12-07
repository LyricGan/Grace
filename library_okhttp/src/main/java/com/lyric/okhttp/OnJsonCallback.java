package com.lyric.okhttp;

import com.lyric.utils.JsonParser;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Json数据请求回调接口
 * @author lyricgan
 */
public abstract class OnJsonCallback<T> extends HttpResponseCallback<T> {

    @Override
    public T parseResponse(ResponseBody responseBody) {
        try {
            return JsonParser.getInstance().getParser().fromJson(responseBody.string(), getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract Type getType();
}
