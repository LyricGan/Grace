package com.lyric.grace.base;

/**
 * common event
 *
 * @author lyricgan
 */
public class CommonEvent {
    public @CommonEventType
    int type;
    public String id;
    public Object object;

    public CommonEvent(@CommonEventType int type) {
        this.type = type;
    }

    public CommonEvent(@CommonEventType int type, String id) {
        this.type = type;
        this.id = id;
    }

    public CommonEvent(@CommonEventType int type, String id, Object object) {
        this.type = type;
        this.id = id;
        this.object = object;
    }
}
