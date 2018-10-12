package com.lyric.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 视图展示工具类
 * @author lyricgan
 */
public class DisplayUtils {

    private DisplayUtils() {
    }

    public static int dip2px(Context context, float dp) {
        float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dip(Context context, float px) {
        float scale = getResources(context).getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static Resources getResources(Context context) {
        return context.getResources();
    }

    public static String toDisplayString() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return "DisplayMetrics [density=" + displayMetrics.density + "," +
                "densityDpi=" + displayMetrics.densityDpi + "," +
                "scaledDensity=" + displayMetrics.scaledDensity + "," +
                "widthPixels=" + displayMetrics.widthPixels + "," +
                "heightPixels=" + displayMetrics.heightPixels + "," +
                "xdpi=" + displayMetrics.xdpi + "," +
                "ydpi=" + displayMetrics.ydpi +
                "]";
    }
}
