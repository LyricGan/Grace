package com.lyric.grace.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences工具类
 * @author lyricgan
 * @date 2017/11/3 9:56
 */
public class GracePreferences {

    private GracePreferences() {
    }
    
    public static SharedPreferences.Editor getEditor(Context context, String name) {
        return getEditor(context, name, Context.MODE_PRIVATE);
    }
    
    public static SharedPreferences.Editor getEditor(Context context, String name, int mode) {
        return getPreferences(context, name, mode).edit();
    }
    
    public static SharedPreferences getDefaultPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getPreferences(Context context, String name) {
        return getPreferences(context, name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getPreferences(Context context, String name, int mode) {
        return context.getSharedPreferences(name, mode);
    }
}