package com.lyric.support.util;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * 安装文件唯一标识
 * @author lyricgan
 */
public class Installation {
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
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            return new String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (f != null) {
                try {
                    f.close();
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
}
