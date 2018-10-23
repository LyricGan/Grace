package com.lyric.utils;

import android.os.Build;
import android.text.TextUtils;

/**
 * 设备相关工具类
 * @author lyricgan
 */
public class DeviceUtils {

    private DeviceUtils() {
    }

    public static String toDeviceString() {
        StringBuilder builder = new StringBuilder();
        builder.append("build string:").append("\n")
                .append("sdk_int:").append(Build.VERSION.SDK_INT).append("\n")
                .append("board:").append(Build.BOARD).append("\n")
                .append("brand:").append(Build.BRAND).append("\n")
                .append("product:").append(Build.PRODUCT).append("\n")
                .append("device:").append(Build.DEVICE).append("\n")
                .append("display:").append(Build.DISPLAY).append("\n")
                .append("hardware:").append(Build.HARDWARE).append("\n")
                .append("id:").append(Build.ID).append("\n")
                .append("manufacturer:").append(Build.MANUFACTURER).append("\n")
                .append("model:").append(Build.MODEL).append("\n")
                .append("bootloader:").append(Build.BOOTLOADER).append("\n")
                .append("type:").append(Build.TYPE).append("\n")
                .append("tags:").append(Build.TAGS).append("\n")
                .append("fingerprint:").append(Build.FINGERPRINT).append("\n")
                .append("time:").append(Build.TIME).append("\n")
                .append("user:").append(Build.USER).append("\n")
                .append("host:").append(Build.HOST).append("\n")
                .append("radioVersion:").append(Build.getRadioVersion()).append("\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("supportedAbi:").append(TextUtils.join(",", Build.SUPPORTED_ABIS)).append("\n");
            builder.append("supported32Abi:").append(TextUtils.join(",", Build.SUPPORTED_32_BIT_ABIS)).append("\n");
            builder.append("supported64Abi:").append(TextUtils.join(",", Build.SUPPORTED_64_BIT_ABIS)).append("\n");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.append("serial:").append(Build.SERIAL);
        }
        return builder.toString();
    }
}
