package com.lyric.grace.network;

import android.text.TextUtils;
import android.util.SparseArray;

/**
 * @author lyricgan
 * @description 状态码
 * @time 2016/8/15 10:17
 */
public class StatusCode {
    public static final int SUCCESS = 0;// 请求成功
    public static final int PARAMS_ERROR = 1;// 参数错误
    public static final int OPERATE_FAILED = 2;// 操作失败
    public static final int NEED_LOGIN = 3;// 未登录或者登录过期
    private static SparseArray<String> mCodeMap;

    static {
        mCodeMap = new SparseArray<>();
    }

    public static String getErrorMessage(int errorCode) {
        String message = mCodeMap.get(errorCode);
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        return message;
    }

    /**
     * 替换已存在的本地默认errorMessage
     *
     * @param errorCode    状态码
     * @param errorMessage 提示信息
     */
    public static void setErrorMessage(int errorCode, String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
            mCodeMap.put(errorCode, errorMessage);
        }
    }
}
