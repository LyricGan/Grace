package com.lyric.grace.data;

import com.lyric.okhttp.OnJsonCallback;
import com.lyric.utils.ClazzUtils;

import java.lang.reflect.Type;

/**
 * @author lyricgan
 * @since 2018/12/7
 */
public abstract class OnJsonResponseCallback<T> extends OnJsonCallback<BaseEntity<T>> {

    public OnJsonResponseCallback(Type type) {
        super(ClazzUtils.typeOf(BaseEntity.class, type));
    }
}
