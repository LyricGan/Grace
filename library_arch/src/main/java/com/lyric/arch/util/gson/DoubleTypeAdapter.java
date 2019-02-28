package com.lyric.arch.util.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 自定义json浮点型数据解析
 *
 * @author lyricgan
 */
class DoubleTypeAdapter extends TypeAdapter<Double> {

    @Override
    public void write(JsonWriter out, Double value) throws IOException {
        out.value(value);
    }

    @Override
    public Double read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0.00d;
        }
        if (in.peek() == JsonToken.BOOLEAN) {
            in.nextBoolean();
            return 0.00d;
        }
        return Utils.parseDouble(in.nextString(), 0.00d);
    }
}
