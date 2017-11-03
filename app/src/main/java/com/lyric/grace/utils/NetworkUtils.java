package com.lyric.grace.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关工具类
 * @author lyricgan
 * @date 2017/11/3 9:56
 */
public class NetworkUtils {

    private NetworkUtils() {
    }

    /**
     * 判断是否有网络连接
     * 需要权限：{@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     *
     * @param context 上下文对象
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] allNetworkInfo = cm.getAllNetworkInfo();
            for (NetworkInfo itemInfo : allNetworkInfo) {
                if (itemInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
