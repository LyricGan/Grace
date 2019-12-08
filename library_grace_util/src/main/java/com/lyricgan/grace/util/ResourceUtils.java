package com.lyricgan.grace.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 资源工具类
 * @author Lyric Gan
 * @since 2019-12-07
 */
public class ResourceUtils {

    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dip(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
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
