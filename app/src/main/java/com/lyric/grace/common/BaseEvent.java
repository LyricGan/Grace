package com.lyric.grace.common;

/**
 * 事件实体
 * @author lyricgan
 * @date 2017/11/3 18:12
 */
public class BaseEvent {
    /** 事件类型 */
    private int type;
    /** 事件参数 */
    private String args;
    /** 事件状态 */
    private boolean status;
    /** 事件传递对象 */
    private Object object;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
