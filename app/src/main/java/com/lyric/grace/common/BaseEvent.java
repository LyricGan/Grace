package com.lyric.grace.common;

/**
 * 事件实体
 * @author lyricgan
 * @date 2017/11/3 18:12
 */
public class BaseEvent {
    private int type;
    private Object object;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
