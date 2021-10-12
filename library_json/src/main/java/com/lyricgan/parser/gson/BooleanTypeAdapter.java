package com.lyricgan.parser.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

class BooleanTypeAdapter extends TypeAdapter<Boolean> {

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        out.value(value);
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return false;
        }
        if (in.peek() == JsonToken.BOOLEAN) {
            return in.nextBoolean();
        }
        if (in.peek() == JsonToken.STRING) {
            try {
                return Boolean.valueOf(in.nextString());
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }
}
