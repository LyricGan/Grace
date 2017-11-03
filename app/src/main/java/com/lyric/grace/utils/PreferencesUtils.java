package com.lyric.grace.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * @author lyricgan
 * @date 2017/11/3 9:56
 */
public class PreferencesUtils {
    private static final String PREFERENCE_NAME = "preferences_default";

    private PreferencesUtils() {
    }
    
    public static SharedPreferences.Editor getPreferencesEditor(Context context, String name) {
        return getPreferencesEditor(context, name, Context.MODE_PRIVATE);
    }
    
    public static SharedPreferences.Editor getPreferencesEditor(Context context, String name, int mode) {
        return getSharedPreferences(context, name, mode).edit();
    }
    
    public static SharedPreferences getSharedPreferences(Context context, String name) {
        return getSharedPreferences(context, name, Context.MODE_PRIVATE);
    }
    
    public static SharedPreferences getSharedPreferences(Context context, String name, int mode) {
        return context.getSharedPreferences(name, mode);
    }

    public static boolean putString(Context context, String key, String value) {
        return getPreferencesEditor(context, PREFERENCE_NAME).putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getSharedPreferences(context, PREFERENCE_NAME).getString(key, defaultValue);
    }

    public static boolean putInt(Context context, String key, int value) {
        return getPreferencesEditor(context, PREFERENCE_NAME).putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getSharedPreferences(context, PREFERENCE_NAME).getInt(key, defaultValue);
    }

    public static boolean putLong(Context context, String key, long value) {
        return getPreferencesEditor(context, PREFERENCE_NAME).putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getSharedPreferences(context, PREFERENCE_NAME).getLong(key, defaultValue);
    }

    public static boolean putFloat(Context context, String key, float value) {
        return getPreferencesEditor(context, PREFERENCE_NAME).putFloat(key, value).commit();
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        return getSharedPreferences(context, PREFERENCE_NAME).getFloat(key, defaultValue);
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
        return getPreferencesEditor(context, PREFERENCE_NAME).putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getSharedPreferences(context, PREFERENCE_NAME).getBoolean(key, defaultValue);
    }

    public static boolean remove(Context context, String key) {
        return getPreferencesEditor(context, PREFERENCE_NAME).remove(key).commit();
    }

    public static boolean clear(Context context) {
        return getPreferencesEditor(context, PREFERENCE_NAME).clear().commit();
    }
}