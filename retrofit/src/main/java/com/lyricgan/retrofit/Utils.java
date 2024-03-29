package com.lyricgan.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class Utils {

    public static boolean isNetworkConnected(Context context) {
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
}
