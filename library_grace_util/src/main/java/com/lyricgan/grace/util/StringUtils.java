package com.lyricgan.grace.util;

import android.net.Uri;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author Lyric Gan
 * @since 2019-12-07
 */
public class StringUtils {

    public static boolean isNumber(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static boolean isFloat(String str) {
        return Pattern.compile("[0-9]*(\\.?)[0-9]*").matcher(str).matches();
    }

    public static String getDouble(double value, int newScale) {
        return new BigDecimal(Double.toString(value))
                .setScale(newScale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    public static byte[] longToBytes(long value) {
        byte[] bytes = new byte[8];
        int len = 8;
        for (byte b : bytes) {
            b = (byte) (value & 0xff);
            bytes[--len] = b;
            value >>= 8;
        }
        return bytes;
    }

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

    /**
     * 对于浮点数进行格式化
     * @param number 浮点数
     * @param decimalsCount 保留的小数位数
     * @param isRetainInteger 如果是整数是否保留小数点后面的0
     * @return 格式化字符串
     */
    public static String formatDecimals(double number, int decimalsCount, boolean isRetainInteger) {
        String value;
        if (isRetainInteger) {
            value = String.format(Locale.getDefault(), "%1$." + decimalsCount + "f", number);
        } else {
            if (Math.round(number) - number == 0) {
                value = String.valueOf((long) number);
            } else {
                value = String.format(Locale.getDefault(), "%1$." + decimalsCount + "f", number);
            }
        }
        return value;
    }

    public static String combineString(String... strings) {
        if (strings == null || strings.length <= 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String arg : strings) {
            builder.append(arg);
        }
        return builder.toString();
    }

    public static <T> String toString(List<T> list) {
        StringBuilder builder = new StringBuilder();
        for (T item : list) {
            builder.append(item.toString()).append("\n");
        }
        return builder.toString();
    }

    public static <K, V> String toString(Map<K, V> map, String split) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry item : map.entrySet()) {
            builder.append(item.getKey()).append(split).append(item.getValue()).append("\n");
        }
        return builder.toString();
    }

    public static <K, V> String getParamsString(String url, Map<K, V> data, String split) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            Set<String> queryParameterNames = uri.getQueryParameterNames();
            if (queryParameterNames != null && !queryParameterNames.isEmpty()) {
                for (String key : queryParameterNames) {
                    builder.append(key).append(split).append(uri.getQueryParameter(key));
                }
            }
        }
        if (data != null && !data.isEmpty()) {
            for (Map.Entry<K, V> entry : data.entrySet()) {
                builder.append(entry.getKey()).append(split).append(entry.getValue());
            }
        }
        return builder.toString();
    }

    public static int getCharLength(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0;
        }
        int valueLength = 0;
        String chineseChar = "[\u0391-\uFFE5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chineseChar)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static boolean containsChineseChar(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        String chineseChar = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chineseChar)) {
                return true;
            }
        }
        return false;
    }
}
