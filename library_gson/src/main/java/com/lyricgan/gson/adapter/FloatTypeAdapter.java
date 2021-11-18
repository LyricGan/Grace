package com.lyricgan.gson.adapter;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 自定义json浮点型数据解析
 * @author Lyric Gan
 */
public class FloatTypeAdapter extends TypeAdapter<Float> {

    @Override
    public void write(JsonWriter out, Float value) throws IOException {
        out.value(value);
    }

    @Override
    public Float read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0.0f;
        }
        return parseFloat(in.nextString());
    }

    private float parseFloat(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0.0f;
        }
        float valueFloat = 0.0f;
        try {
            valueFloat = Float.parseFloat(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueFloat;
    }
}
