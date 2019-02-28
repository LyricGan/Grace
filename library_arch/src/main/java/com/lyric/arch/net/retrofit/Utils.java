package com.lyric.arch.net.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author lyricgan
 */
public final class Utils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo[] networkInfoItems = cm.getAllNetworkInfo();
        if (networkInfoItems != null) {
            for (NetworkInfo itemInfo : networkInfoItems) {
                if (itemInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
