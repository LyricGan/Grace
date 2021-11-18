package com.lyricgan.gson.adapter;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 自定义json长整型数据解析
 * @author Lyric Gan
 */
public class LongTypeAdapter extends TypeAdapter<Long> {

    @Override
    public void write(JsonWriter out, Long value) throws IOException {
        out.value(value);
    }

    @Override
    public Long read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0L;
        }
        return parseLong(in.nextString());
    }

    private long parseLong(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0L;
        }
        long valueLong = 0L;
        try {
            valueLong = Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueLong;
    }
}
