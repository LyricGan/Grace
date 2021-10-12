package com.lyricgan.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 富文本字符串工具类
 * @author Lyric Gan
 */
public class SpannableStringUtils {

    /**
     * 获取指定位置缩放后的字符串
     * @param source 源字符串
     * @param scale 缩放倍数
     * @param start 起始位置
     * @param end 结束位置
     * @param flags 例如Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
     * @return 指定位置缩放后的字符串
     */
    public static SpannableString setSubRelativeSizeSpan(CharSequence source, float scale, int start, int end, int flags) {
        return setSubCharacterStyle(source, new RelativeSizeSpan(scale), start, end, flags);
    }

    public static SpannableString setSubAbsoluteSizeSpan(CharSequence source, int size, int start, int end, int flags) {
        return setSubAbsoluteSizeSpan(source, size, false, start, end, flags);
    }

    public static SpannableString setSubAbsoluteSizeSpan(CharSequence source, int size, boolean dip, int start, int end, int flags) {
        return setSubCharacterStyle(source, new AbsoluteSizeSpan(size, dip), start, end, flags);
    }

    public static SpannableString setSubForegroundColorSpan(CharSequence source, int color, int start, int end, int flags) {
        return setSubCharacterStyle(source, new ForegroundColorSpan(color), start, end, flags);
    }

    public static SpannableString setSubBackgroundColorSpan(CharSequence source, int color, int start, int end, int flags) {
        return setSubCharacterStyle(source, new BackgroundColorSpan(color), start, end, flags);
    }

    public static SpannableString setSubStyleSpan(CharSequence source, int style, int start, int end, int flags) {
        return setSubCharacterStyle(source, new StyleSpan(style), start, end, flags);
    }

    public static SpannableString setSubTypefaceSpan(CharSequence source, String family, int start, int end, int flags) {
        return setSubCharacterStyle(source, new TypefaceSpan(family), start, end, flags);
    }

    public static SpannableString setSubURLSpan(CharSequence source, String url, int start, int end, int flags) {
        return setSubCharacterStyle(source, new URLSpan(url), start, end, flags);
    }

    public static SpannableString setSubScaleXSpan(CharSequence source, float proportion, int start, int end, int flags) {
        return setSubCharacterStyle(source, new ScaleXSpan(proportion), start, end, flags);
    }

    public static SpannableString setSubImageSpan(CharSequence source, Drawable drawable, int start, int end, int flags) {
        return setSubImageSpan(source, drawable, ImageSpan.ALIGN_BOTTOM, start, end, flags);
    }

    public static SpannableString setSubImageSpan(CharSequence source, Drawable drawable, int verticalAlignment, int start, int end, int flags) {
        return setSubCharacterStyle(source, new ImageSpan(drawable, verticalAlignment), start, end, flags);
    }

    public static SpannableString setSubClickableSpan(CharSequence source, ClickableSpan clickableSpan, int start, int end, int flags) {
        return setSubCharacterStyle(source, clickableSpan, start, end, flags);
    }

    public static SpannableString setSubCharacterStyle(CharSequence source, CharacterStyle characterStyle, int start, int end, int flags) {
        return setSubSpan(source, characterStyle, start, end, flags);
    }

    public static SpannableString setSubSpan(CharSequence source, Object object, int start, int end, int flags) {
        if (source == null || object == null) {
            return null;
        }
        if (start < 0 || end < 0 || start >= end) {
            return null;
        }
        SpannableString spannableString = SpannableString.valueOf(source);
        spannableString.setSpan(object, start, end, flags);
        return spannableString;
    }

    public static SpannableStringBuilder append(String text, int textColor, int textDimensionId) {
        int textSize = Resources.getSystem().getDimensionPixelSize(textDimensionId);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        String str = stringBuilder.toString();
        stringBuilder.append(text);
        int start = str.length();
        int end = start + text.length();
        stringBuilder.setSpan(new ForegroundColorSpan(textColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new AbsoluteSizeSpan(textSize), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }

    /**
     * 关键字高亮变色
     * @param text    文字
     * @param keyword 文字中的关键字
     * @param color   变化的色值
     * @return SpannableString
     */
    public static SpannableString matcherText(String text, String keyword, int color) {
        return matcherText(text, keyword, color, null);
    }

    public static SpannableString matcherText(String text, String keyword, int color, CharacterStyle characterStyle) {
        SpannableString s = new SpannableString(text);
        return matcherIn(s, keyword, color, characterStyle);
    }

    /**
     * 多个关键字高亮变色
     * @param text    文字
     * @param keywords 文字中的关键字数组
     * @param color   变化的色值
     * @return SpannableString
     */
    public static SpannableString matcherText(String text, String[] keywords, int color) {
        return matcherText(text, keywords, color, null);
    }

    public static SpannableString matcherText(String text, String[] keywords, int color, CharacterStyle characterStyle) {
        SpannableString s = new SpannableString(text);
        for (String keyword: keywords) {
            s = matcherIn(s, keyword, color, characterStyle);
        }
        return s;
    }

    private static SpannableString matcherIn(SpannableString s, String keyword, int color, CharacterStyle characterStyle) {
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        if (characterStyle == null) {
            characterStyle = new ForegroundColorSpan(color);
        }
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(characterStyle, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }
}
