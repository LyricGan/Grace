package com.lyricgan.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络工具类
 * @author Lyric Gan
 */
@SuppressLint("MissingPermission")
public class NetworkUtils {

    /**
     * 判断是否有网络连接，需要权限：{@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     * @param context 上下文对象
     * @return true or false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo[] networkInfoItems = cm.getAllNetworkInfo();
        for (NetworkInfo itemInfo : networkInfoItems) {
            if (itemInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断手机网络是否可用，需要权限：{@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     * @param context 上下文对象
     * @return true or false
     */
    public static boolean isMobileEnabled(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * 判断Wifi网络是否可用，需要权限：{@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     * @param context 上下文对象
     * @return true or false
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * 判断网络是否为漫游，需要权限：{@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     * @param context 上下文对象
     * @return true or false
     */
    public static boolean isNetworkRoaming(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null && tm.isNetworkRoaming();
        }
        return false;
    }
}
