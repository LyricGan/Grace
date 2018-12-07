package com.lyric.grace.data;

import com.lyric.okhttp.OnJsonCallback;
import com.lyric.utils.ClazzUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author lyricgan
 * @since 2018/12/7
 */
public abstract class OnJsonResponseCallback<T> extends OnJsonCallback<BaseEntity<T>> {

    @Override
    public Type getType() {
        Type type = null;
        try {
            Field data = BaseEntity.class.getDeclaredField("data");
            type = ClazzUtils.typeOf(BaseEntity.class, null, data.getGenericType());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }
}
