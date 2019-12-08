package com.lyricgan.grace.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * @author Lyric Gan
 * @since 2019-12-07
 */
public class TimeUtils {
    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = 60 * ONE_SECOND;
    public static final int ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;
    public static final long ONE_WEEK = 7 * ONE_DAY;

    public static final String PATTERN_ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_TIME = "HH:mm";

    public static DateFormat dateFormat(String pattern, Locale locale) {
        return new SimpleDateFormat(pattern, locale);
    }

    public static String getFormatTime(long milliseconds, DateFormat dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return dateFormat.format(calendar.getTime());
    }

    public static long getTimeInMillis(String dateString, DateFormat dateFormat) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(dateString);
            if (date != null) {
                calendar.setTime(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static int getField(long milliseconds, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar.get(field);
    }
}
