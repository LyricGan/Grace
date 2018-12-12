package com.lyric.utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 自定义json整型数据解析
 *
 * @author lyricgan
 */
class IntegerTypeAdapter extends TypeAdapter<Integer> {

    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        out.value(value);
    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0;
        }
        if (in.peek() == JsonToken.BOOLEAN) {
            in.nextBoolean();
            return 0;
        }
        return Utils.parseInt(in.nextString(), 0);
    }
}
