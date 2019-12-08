package com.lyricgan.grace.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * 设备工具类
 * @author Lyric Gan
 * @since 2019-12-07
 */
public class DeviceUtils {
    private static final String INSTALLATION = "INSTALLATION";
    private static String mId;

    public static synchronized String getId(Context context) {
        if (mId == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            if (!installation.exists()) {
                writeInstallationFile(installation);
            }
            mId = readInstallationFile(installation);
        }
        return mId;
    }

    private static String readInstallationFile(File file) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(bytes);
            return new String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static void writeInstallationFile(File file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            String id = UUID.randomUUID().toString();
            out.write(id.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
