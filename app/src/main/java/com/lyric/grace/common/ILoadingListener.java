package com.lyric.grace.common;

/**
 * 加载监听接口
 * @author lyricgan
 * @date 2017/11/3 9:48
 */
public interface ILoadingListener {

    void showLoading(CharSequence charSequence, boolean isCancelable);

    void hideLoading();
}
