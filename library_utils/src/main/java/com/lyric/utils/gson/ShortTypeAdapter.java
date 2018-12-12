package com.lyric.utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author ganyu
 * @date 2017/7/25 10:21
 */
class ShortTypeAdapter extends TypeAdapter<Short> {

    @Override
    public void write(JsonWriter out, Short value) throws IOException {
        out.value(value);
    }

    @Override
    public Short read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0;
        }
        return Utils.parseShort(in.nextString(), (short) 0);
    }
}
