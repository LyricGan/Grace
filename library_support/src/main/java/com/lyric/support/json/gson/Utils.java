package com.lyric.support.json.gson;

import android.text.TextUtils;

/**
 * @author lyricgan
 * @since 2018/12/10
 */
final class Utils {
    static final String EMPTY = "";

    public static int parseInt(String value, int defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        int valueInt = defaultValue;
        try {
            valueInt = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                valueInt = Double.valueOf(value).intValue();
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
            }
        }
        return valueInt;
    }

    public static float parseFloat(String value, float defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        float valueFloat = defaultValue;
        try {
            valueFloat = Float.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueFloat;
    }

    public static double parseDouble(String value, double defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        double valueDouble = defaultValue;
        try {
            valueDouble = Double.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueDouble;
    }

    public static long parseLong(String value, long defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        long valueLong = defaultValue;
        try {
            valueLong = Long.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueLong;
    }

    public static short parseShort(String value, short defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        short valueShort = defaultValue;
        try {
            valueShort = Short.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return valueShort;
    }
}
