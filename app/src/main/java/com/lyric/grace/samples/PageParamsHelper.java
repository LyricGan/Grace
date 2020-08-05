package com.lyric.grace.samples;

import android.os.Bundle;

/**
 * 参数封装工具类，将不同的页面参数统一封装，方便维护
 *
 * @author Lyric Gan
 * @since 2019-08-15
 */
public class PageParamsHelper {

    private static Bundle newParams() {
        return new Bundle();
    }

    public static Bundle getIdParams(String id) {
        Bundle params = newParams();
        params.putString(IExtras.KEY_ID, id);
        return params;
    }
}
