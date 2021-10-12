package com.lyricgan.parser.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

class LongTypeAdapter extends TypeAdapter<Long> {

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
        if (in.peek() == JsonToken.BOOLEAN) {
            in.nextBoolean();
            return 0L;
        }
        return Utils.parseLong(in.nextString(), 0L);
    }
}
