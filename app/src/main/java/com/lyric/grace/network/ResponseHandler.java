package com.lyric.grace.network;

import android.text.TextUtils;

import com.lyric.grace.GraceApplication;
import com.lyric.grace.utils.ToastUtils;

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

    public static void process(String errorMessage) {
        ToastUtils.showShort(GraceApplication.getContext(), errorMessage);
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
        ToastUtils.showShort(GraceApplication.getContext(), errorMessage);
    }
}
