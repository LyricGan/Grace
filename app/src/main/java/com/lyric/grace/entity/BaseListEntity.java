package com.lyric.grace.entity;

/**
 * @author lyricgan
 * @description
 * @time 2016/8/3 11:41
 */
public class BaseListEntity<T> {
    private int errorCode;
    private String errorMessage;
    private BaseListWrapperEntity<T> result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BaseListWrapperEntity<T> getResult() {
        return result;
    }

    public void setResult(BaseListWrapperEntity<T> result) {
        this.result = result;
    }
}
