package com.lyricgan.grace.json.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

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
