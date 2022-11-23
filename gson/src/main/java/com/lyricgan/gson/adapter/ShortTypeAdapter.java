package com.lyricgan.gson.adapter;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 自定义json短整型数据解析
 * @author Lyric Gan
 */
public class ShortTypeAdapter extends TypeAdapter<Short> {

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
        return parseShort(in.nextString());
    }

    private short parseShort(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0;
        }
        short valueShort = 0;
        try {
            valueShort = Short.parseShort(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueShort;
    }
}
