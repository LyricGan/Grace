package com.lyric.arch.net.app;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * converter for json
 * @author lyricgan
 */
public class GsonConverter implements Converter {
    private final Gson mGson;
    private static GsonConverter mInstance;

    private GsonConverter() {
        mGson = new Gson();
    }

    public static synchronized GsonConverter getInstance() {
        if (mInstance == null) {
            mInstance = new GsonConverter();
        }
        return mInstance;
    }

    @Override
    public <T> T convert(String json, Type type) {
        // 字符串直接返回，不做转换处理
        if (String.class.getClass().equals(type.getClass())) {
            return  (T) json;
        }
        T result = null;
        try {
            result = mGson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }
}
