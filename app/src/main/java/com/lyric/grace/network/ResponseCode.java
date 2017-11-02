package com.lyric.grace.network;

import android.text.TextUtils;
import android.util.SparseArray;

/**
 * 状态码
 * @author lyricgan
 * @time 2016/8/15 10:17
 */
public class ResponseCode {
    public static final int SUCCESS = 0;
    public static final int PARAMS_ERROR = 1;
    public static final int OPERATE_FAILED = 2;
    public static final int LOGIN_INVALID = 3;

    private static SparseArray<String> mResponseCodeArray = new SparseArray<>();

    public static String getErrorMessage(int errorCode) {
        return mResponseCodeArray.get(errorCode);
    }

    /**
     * 替换已存在的本地默认errorMessage
     *
     * @param errorCode    状态码
     * @param errorMessage 提示信息
     */
    public static void setErrorMessage(int errorCode, String errorMessage) {
        if (TextUtils.isEmpty(errorMessage)) {
            return;
        }
        mResponseCodeArray.put(errorCode, errorMessage);
    }
}
