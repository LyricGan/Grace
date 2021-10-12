package com.lyricgan.eventbus;

/**
 * 默认通知事件，接收事件类型和事件参数
 */
public class CommonEvent {
    private int arg1;
    private String arg2;
    private Object arg3;

    public CommonEvent(int arg1) {
        this.arg1 = arg1;
    }

    public CommonEvent(int arg1, String arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public CommonEvent(int arg1, String arg2, Object arg3) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public Object getArg3() {
        return arg3;
    }

    public void setArg3(Object arg3) {
        this.arg3 = arg3;
    }
}
