package com.lyricgan.grace.samples.util;

import android.os.Bundle;

import com.lyricgan.grace.samples.constants.IExtras;

/**
 * 参数封装工具类，将不同的页面参数统一封装，方便维护
 *
 * @author Lyric Gan
 */
public class PageParamsHelper {

    private PageParamsHelper() {
    }

    private static Bundle newParams() {
        return new Bundle();
    }

    public static Bundle getParams(String id) {
        Bundle params = newParams();
        params.putString(IExtras.KEY_ID, id);
        return params;
    }
}
