package com.lyric.grace.entity;

/**
 * @author lyricgan
 * @description
 * @time 2016/9/1 15:47
 */
public class HomeItemEntity {
    private String title;
    private Class<?> clazz;
    private String color;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
