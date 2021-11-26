package com.lyricgan.grace.samples.util;

import android.os.Bundle;

import com.lyricgan.grace.samples.constants.IExtras;

/**
 * 参数封装工具类，将不同的页面参数统一封装，方便维护
 *
 * @author Lyric Gan
 */
public class ParamsHelper {

    private ParamsHelper() {
    }

    public static Bundle getParams(String id) {
        Bundle params = new Bundle();
        params.putString(IExtras.KEY_ID, id);
        return params;
    }
}
