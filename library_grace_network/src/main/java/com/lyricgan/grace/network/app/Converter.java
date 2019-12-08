package com.lyricgan.grace.network.app;

import java.lang.reflect.Type;

public interface Converter {

    <T> T convert(String json, Type type);
}
