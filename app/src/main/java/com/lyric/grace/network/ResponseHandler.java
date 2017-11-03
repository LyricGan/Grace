package com.lyric.grace.network;

import android.text.TextUtils;

/**
 * @author lyricgan
 * @description 响应数据处理类
 * @time 2016/7/27 12:40
 */
public class ResponseHandler {

    private ResponseHandler() {
    }

    public static void process(int errorCode) {
        process(errorCode, "");
    }

    /**
     * 统一通过错误码来处理错误提示
     * @param errorCode 错误码
     * @param errorMessage 错误提示信息
     */
    public static void process(int errorCode, String errorMessage) {
        if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = ResponseCode.getErrorMessage(errorCode);
        }
    }
}
