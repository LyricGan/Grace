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
public class DoubleTypeAdapter extends TypeAdapter<Double> {

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
        return parseDouble(in.nextString());
    }

    private double parseDouble(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0.00d;
        }
        double valueDouble = 0.00d;
        try {
            valueDouble = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueDouble;
    }
}
