package com.lyric.grace.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.lyric.grace.GraceApplication;

/**
 * 文件工具类
 * @author lyricgan
 * @date 2017/11/3 13:22
 */
public class FileUtils {

    private FileUtils() {
    }

    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getRootDirectory() {
        return getRootDirectory(GraceApplication.getContext());
    }

    public static String getRootDirectory(Context context) {
        String rootDirectory;
        if (isSdcardExist()) {
            rootDirectory = Environment.getExternalStorageDirectory().getPath();
        } else {
            rootDirectory = context.getCacheDir().getPath();
        }
        if (TextUtils.isEmpty(rootDirectory)) {
            rootDirectory = "";
        }
        return rootDirectory;
    }
}
