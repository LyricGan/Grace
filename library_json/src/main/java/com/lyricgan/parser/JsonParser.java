package com.lyricgan.parser;

import com.google.gson.Gson;

/**
 * json解析工具类，使用Gson实现
 * @author Lyric Gan
 */
public class JsonParser {
    private Gson mParser;

    private static class JsonParserHolder {
        private static final JsonParser JSON_HELPER = new JsonParser();
    }

    private JsonParser() {
        mParser = new Gson();
    }

    public static JsonParser getInstance() {
        return JsonParserHolder.JSON_HELPER;
    }

    public Gson getParser() {
        return mParser;
    }

    public void setParser(Gson parser) {
        this.mParser = parser;
    }
}
