package com.lyricgan.gson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 自定义json布尔类型解析
 * @author Lyric Gan
 */
public class BooleanTypeAdapter extends TypeAdapter<Boolean> {

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        out.value(value);
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        if (token == JsonToken.BOOLEAN) {
            return in.nextBoolean();
        }
        if (token == JsonToken.NULL) {
            in.nextNull();
            return false;
        }
        if (token == JsonToken.STRING) {
            try {
                return Boolean.valueOf(in.nextString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }
}
