package com.lyric.arch.json.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 自定义json字符串解析
 *
 * @author lyricgan
 */
class StringTypeAdapter extends TypeAdapter<String> {

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return Utils.EMPTY;
        }
        if (in.peek() == JsonToken.BOOLEAN) {
            in.nextBoolean();
            return Utils.EMPTY;
        }
        return in.nextString();
    }
}
