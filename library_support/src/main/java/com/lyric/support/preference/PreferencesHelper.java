package com.lyric.support.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences helper
 *
 * @author lyricgan
 */
public class PreferencesHelper {

    private PreferencesHelper() {
    }

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getPreferences(Context context, String name) {
        return getPreferences(context, name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getPreferences(Context context, String name, int mode) {
        return context.getSharedPreferences(name, mode);
    }

    public static SharedPreferences.Editor getEditor(Context context, String name) {
        return getEditor(context, name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context, String name, int mode) {
        return getPreferences(context, name, mode).edit();
    }
}
