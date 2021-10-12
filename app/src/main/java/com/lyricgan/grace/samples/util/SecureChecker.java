package com.lyricgan.grace.samples.util;

import android.os.Process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * 安全检查工具类
 *
 * @author Lyric Gan
 */
public class SecureChecker {
    private static final String XPOSED_JAR = "XposedBridge.jar";
    private static final String XPOSED_KEY = "de.robv.android.xposed.XposedBridge";
    private static final String SUBSTRATE_KEY = "com.saurik.substrate";
    private static final String SUBSTRATE_KEY_2 = "com.saurik.substrate.MS$2";

    public static boolean checkXPosed() {
        return read(XPOSED_JAR) && isInstallXPosed();
    }

    public static boolean checkSubstrate() {
        return read(SUBSTRATE_KEY) && isInstallSubstrate();
    }

    private static boolean read(String str) {
        try {
            String readLine;
            Set<String> hashSet = new HashSet<>();
            String path = "/proc/" + Process.myPid() + "/maps";
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (true) {
                readLine = reader.readLine();
                if (readLine == null) {
                    break;
                } else if (readLine.endsWith(".so") || readLine.endsWith(".jar")) {
                    hashSet.add(readLine.substring(readLine.lastIndexOf(".") + 1));
                }
            }
            reader.close();
            for (String readLine2 : hashSet) {
                if (readLine2.contains(str)) {
                    return true;
                }
            }
        } catch (Throwable t) {
            return false;
        }
        return false;
    }

    private static boolean isInstallXPosed() {
        try {
            throw new Exception("");
        } catch (Exception e) {
            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getClassName().equals(XPOSED_KEY)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static boolean isInstallSubstrate() {
        try {
            throw new Exception("");
        } catch (Exception e) {
            int i = 0;
            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    i++;
                    if (i == 2) {
                        return true;
                    }
                }
                if (element.getClassName().equals(SUBSTRATE_KEY_2)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isRoot() {
        int secureProp = getRoSecureProperty();
        if (secureProp == 0) {
            return true;
        }
        return isSuExists();
    }

    private static int getRoSecureProperty() {
        int secureProperty;
        String roSecureObj = System.getProperty("ro.secure");
        if (roSecureObj == null) {
            secureProperty = 1;
        } else {
            secureProperty = ("0".equals(roSecureObj)) ? 0 : 1;
        }
        return secureProperty;
    }

    private static boolean isSuExists() {
        String[] paths = {"/sbin/su", "/system/bin/su", "/system/xbin/su",
                "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su"};
        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }
}
