package com.lyric.support.net.app;

import java.lang.reflect.Type;

/**
 * @author lyricgan
 * @time 2016/8/5 14:05
 */
public interface Converter {

    <T> T convert(String json, Type type);
}
