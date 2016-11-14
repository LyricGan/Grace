package com.lyric.grace.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 基础接口类
 * @author lyricgan
 * @version 2015-9-24
 */
public interface IBaseListener {

    /**
     * 在super.onCreate()之前调用
     * @param savedInstanceState bundles
     */
    void onPrepareCreate(Bundle savedInstanceState);

    /**
     * 获取布局文件ID
     * @return 布局文件ID
     */
    @LayoutRes int getLayoutId();

	/**
	 * 初始化布局界面
	 * @param savedInstanceState bundles
	 */
	void onLayoutCreated(Bundle savedInstanceState);

	/**
	 * 组件点击事件处理
	 * @param v view
	 */
	void onViewClick(View v);
}