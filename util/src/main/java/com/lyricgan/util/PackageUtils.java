package com.lyricgan.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 包管理工具类
 * <ul>
 * <strong>Install package</strong>
 * <li>{@link PackageUtils#installNormal(Context, String)}</li>
 * <li>{@link PackageUtils#installSilent(Context, String)}</li>
 * <li>{@link PackageUtils#install(Context, String)}</li>
 * </ul>
 * <ul>
 * <strong>Uninstall package</strong>
 * <li>{@link PackageUtils#uninstallNormal(Context, String)}</li>
 * <li>{@link PackageUtils#uninstallSilent(Context, String)}</li>
 * <li>{@link PackageUtils#uninstall(Context, String)}</li>
 * </ul>
 * <ul>
 * <strong>Is system application</strong>
 * <li>{@link PackageUtils#isSystemApplication(Context)}</li>
 * <li>{@link PackageUtils#isSystemApplication(Context, String)}</li>
 * <li>{@link PackageUtils#isSystemApplication(PackageManager, String)}</li>
 * </ul>
 * <ul>
 * <strong>Others</strong>
 * <li>{@link PackageUtils#getInstallLocation()} get system install location</li>
 * <li>{@link PackageUtils#isTopActivity(Context, String)} whether the app whost
 * package's name is packageName is on the top of the stack</li>
 * <li>{@link PackageUtils#startInstalledAppDetails(Context, String)} start
 * InstalledAppDetails Activity</li>
 * </ul>
 * @author Lyric Gan
 */
public class PackageUtils {
    public static final String TAG = PackageUtils.class.getSimpleName();
    /** 应用安装位置：自动 */
    public static final int APP_INSTALL_AUTO = 0;
    /** 应用安装位置：内部存储 */
    public static final int APP_INSTALL_INTERNAL = 1;
    /** 应用安装位置：外部存储 */
    public static final int APP_INSTALL_EXTERNAL = 2;

    /** 应用安装返回码：安装成功 */
    public static final int INSTALL_SUCCEEDED = 1;
    /** 应用安装返回码：应用已存在 */
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    /** 应用安装返回码：无效的APK */
    public static final int INSTALL_FAILED_INVALID_APK = -2;
    /** 应用安装返回码：无效的URI */
    public static final int INSTALL_FAILED_INVALID_URI = -3;

    /**
     * Installation return code<br/>
     * the package manager service found that the device didn't have enough
     * storage space to install the app.
     */
    public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;

    /**
     * Installation return code<br/>
     * a package is already installed with the same name.
     */
    public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;

    /**
     * Installation return code<br/>
     * the requested shared user does not exist.
     */
    public static final int INSTALL_FAILED_NO_SHARED_USER = -6;

    /**
     * Installation return code<br/>
     * a previously installed package of the same name has a different signature
     * than the new package (and the old package's data was not removed).
     */
    public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;

    /**
     * Installation return code<br/>
     * the new package is requested a shared user which is already installed on
     * the device and does not have matching signature.
     */
    public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;

    /**
     * Installation return code<br/>
     * the new package uses a shared library that is not available.
     */
    public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;

    /**
     * Installation return code<br/>
     * the new package uses a shared library that is not available.
     */
    public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;

    /**
     * Installation return code<br/>
     * the new package failed while optimizing and validating its dex files,
     * either because there was not enough storage or the validation failed.
     */
    public static final int INSTALL_FAILED_DEXOPT = -11;

    /**
     * Installation return code<br/>
     * the new package failed because the current SDK version is older than that
     * required by the package.
     */
    public static final int INSTALL_FAILED_OLDER_SDK = -12;

    /**
     * Installation return code<br/>
     * the new package failed because it contains a content provider with the
     * same authority as a provider already installed in the system.
     */
    public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;

    /**
     * Installation return code<br/>
     * the new package failed because the current SDK version is newer than that
     * required by the package.
     */
    public static final int INSTALL_FAILED_NEWER_SDK = -14;

    /**
     * Installation return code<br/>
     * the new package failed because it has specified that it is a test-only
     * package and the caller has not supplied the
     * flag.
     */
    public static final int INSTALL_FAILED_TEST_ONLY = -15;

    /**
     * Installation return code<br/>
     * the package being installed contains native code, but none that is
     * compatible with the the device's CPU_ABI.
     */
    public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;

    /**
     * Installation return code<br/>
     * the new package uses a feature that is not available.
     */
    public static final int INSTALL_FAILED_MISSING_FEATURE = -17;

    /**
     * Installation return code<br/>
     * a secure container mount point couldn't be accessed on external media.
     */
    public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;

    /**
     * Installation return code<br/>
     * the new package couldn't be installed in the specified install location.
     */
    public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;

    /**
     * Installation return code<br/>
     * the new package couldn't be installed in the specified install location
     * because the media is not available.
     */
    public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;

    /**
     * Installation return code<br/>
     * the new package couldn't be installed because the verification timed out.
     */
    public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;

    /**
     * Installation return code<br/>
     * the new package couldn't be installed because the verification did not
     * succeed.
     */
    public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;

    /**
     * Installation return code<br/>
     * the package changed from what the calling program expected.
     */
    public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;

    /**
     * Installation return code<br/>
     * the new package is assigned a different UID than it previously held.
     */
    public static final int INSTALL_FAILED_UID_CHANGED = -24;

    /**
     * Installation return code<br/>
     * if the parser was given a path that is not a file, or does not end with
     * the expected '.apk' extension.
     */
    public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;

    /**
     * Installation return code<br/>
     * if the parser was unable to retrieve the AndroidManifest.xml file.
     */
    public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;

    /**
     * Installation return code<br/>
     * if the parser encountered an unexpected exception.
     */
    public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;

    /**
     * Installation return code<br/>
     * if the parser did not find any certificates in the .apk.
     */
    public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;

    /**
     * Installation return code<br/>
     * if the parser found inconsistent certificates on the files in the .apk.
     */
    public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;

    /**
     * Installation return code<br/>
     * if the parser encountered a CertificateEncodingException in one of the
     * files in the .apk.
     */
    public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;

    /**
     * Installation return code<br/>
     * if the parser encountered a bad or missing package name in the manifest.
     */
    public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;

    /**
     * Installation return code<br/>
     * if the parser encountered a bad shared user id name in the manifest.
     */
    public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;

    /**
     * Installation return code<br/>
     * if the parser encountered some structural problem in the manifest.
     */
    public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;

    /**
     * Installation return code<br/>
     * if the parser did not find any actionable tags (instrumentation or
     * application) in the manifest.
     */
    public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;

    /**
     * Installation return code<br/>
     * if the system failed to install the package because of system issues.
     */
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
    /**
     * Installation return code<br/>
     * other reason
     */
    public static final int INSTALL_FAILED_OTHER = -1000000;

    /**
     * Uninstall return code<br/>
     * uninstall success.
     */
    public static final int DELETE_SUCCEEDED = 1;

    /**
     * Uninstall return code<br/>
     * uninstall fail if the system failed to delete the package for an
     * unspecified reason.
     */
    public static final int DELETE_FAILED_INTERNAL_ERROR = -1;

    /**
     * Uninstall return code<br/>
     * uninstall fail if the system failed to delete the package because it is
     * the active DevicePolicy manager.
     */
    public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;

    /**
     * Uninstall return code<br/>
     * uninstall fail if pcakge name is invalid
     */
    public static final int DELETE_FAILED_INVALID_PACKAGE = -3;

    /**
     * Uninstall return code<br/>
     * uninstall fail if permission denied
     */
    public static final int DELETE_FAILED_PERMISSION_DENIED = -4;

    private PackageUtils() {
    }

    /**
     * install according conditions
     * <ul>
     * <li>if system application or rooted, see
     * {@link #installSilent(Context, String)}</li>
     * <li>else see {@link #installNormal(Context, String)}</li>
     * </ul>
     *
     * @param context the environment of context
     * @param filePath file path of package
     * @return code of install result
     */
    public static int install(Context context, String filePath) {
        if (isSystemApplication(context) || ShellUtils.checkRootPermission()) {
            return installSilent(context, filePath);
        }
        return installNormal(context, filePath) ? INSTALL_SUCCEEDED : INSTALL_FAILED_INVALID_URI;
    }

    /**
     * install package normal by system intent
     * @param context the environment of context
     * @param filePath file path of package
     * @return whether apk exist
     */
    public static boolean installNormal(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }
        intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

    /**
     * install package silent by root
     * <ul>
     * <strong>Attentions:</strong>
     * <li>Don't call this on the ui thread, it may costs some times.</li>
     * <li>You should add <strong>android.permission.INSTALL_PACKAGES</strong>
     * in manifest, so no need to request root permission, if you are system
     * app.</li>
     * <li>Default pm install params is "-r".</li>
     * </ul>
     *
     * @param context the environment of context
     * @param filePath file path of package
     * @return {@link PackageUtils#INSTALL_SUCCEEDED} means install success,
     *         other means failed. details see {@link PackageUtils}
     *         .INSTALL_FAILED_*. same to {@link PackageManager}.INSTALL_*
     * @see #installSilent(Context, String, String)
     */
    public static int installSilent(Context context, String filePath) {
        return installSilent(context, filePath, " -r " + getInstallLocationParams());
    }

    /**
     * install package silent by root
     * <ul>
     * <strong>Attentions:</strong>
     * <li>Don't call this on the ui thread, it may costs some times.</li>
     * <li>You should add <strong>android.permission.INSTALL_PACKAGES</strong>
     * in manifest, so no need to request root permission, if you are system
     * app.</li>
     * </ul>
     *
     * @param context the environment of context
     * @param filePath file path of package
     * @param pmParams pm install params
     * @return {@link PackageUtils#INSTALL_SUCCEEDED} means install success,
     *         other means failed. details see {@link PackageUtils}
     *         .INSTALL_FAILED_*. same to {@link PackageManager}.INSTALL_*
     */
    public static int installSilent(Context context, String filePath, String pmParams) {
        if (filePath == null || filePath.length() == 0) {
            return INSTALL_FAILED_INVALID_URI;
        }
        File file = new File(filePath);
        if (file.length() <= 0 || !file.exists() || !file.isFile()) {
            return INSTALL_FAILED_INVALID_URI;
        }
        /**
         * if context is system app, don't need root permission, but should add
         * <uses-permission android:name="android.permission.INSTALL_PACKAGES" /> in mainfest
         **/
        StringBuilder command = new StringBuilder()
                .append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ")
                .append(pmParams == null ? "" : pmParams).append(" ")
                .append(filePath.replace(" ", "\\ "));
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(
                command.toString(), !isSystemApplication(context), true);
        if (commandResult.successMsg != null && (commandResult.successMsg.contains("Success") || commandResult.successMsg.contains("success"))) {
            return INSTALL_SUCCEEDED;
        }
        Log.e(TAG, new StringBuilder().append("installSilent successMsg:")
                .append(commandResult.successMsg).append(", ErrorMsg:")
                .append(commandResult.errorMsg).toString());
        if (commandResult.errorMsg == null) {
            return INSTALL_FAILED_OTHER;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_ALREADY_EXISTS")) {
            return INSTALL_FAILED_ALREADY_EXISTS;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_APK")) {
            return INSTALL_FAILED_INVALID_APK;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_URI")) {
            return INSTALL_FAILED_INVALID_URI;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INSUFFICIENT_STORAGE")) {
            return INSTALL_FAILED_INSUFFICIENT_STORAGE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_DUPLICATE_PACKAGE")) {
            return INSTALL_FAILED_DUPLICATE_PACKAGE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_NO_SHARED_USER")) {
            return INSTALL_FAILED_NO_SHARED_USER;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_UPDATE_INCOMPATIBLE")) {
            return INSTALL_FAILED_UPDATE_INCOMPATIBLE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_SHARED_USER_INCOMPATIBLE")) {
            return INSTALL_FAILED_SHARED_USER_INCOMPATIBLE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_MISSING_SHARED_LIBRARY")) {
            return INSTALL_FAILED_MISSING_SHARED_LIBRARY;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_REPLACE_COULDNT_DELETE")) {
            return INSTALL_FAILED_REPLACE_COULDNT_DELETE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_DEXOPT")) {
            return INSTALL_FAILED_DEXOPT;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_OLDER_SDK")) {
            return INSTALL_FAILED_OLDER_SDK;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_CONFLICTING_PROVIDER")) {
            return INSTALL_FAILED_CONFLICTING_PROVIDER;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_NEWER_SDK")) {
            return INSTALL_FAILED_NEWER_SDK;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_TEST_ONLY")) {
            return INSTALL_FAILED_TEST_ONLY;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_CPU_ABI_INCOMPATIBLE")) {
            return INSTALL_FAILED_CPU_ABI_INCOMPATIBLE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_MISSING_FEATURE")) {
            return INSTALL_FAILED_MISSING_FEATURE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_CONTAINER_ERROR")) {
            return INSTALL_FAILED_CONTAINER_ERROR;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_INSTALL_LOCATION")) {
            return INSTALL_FAILED_INVALID_INSTALL_LOCATION;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_MEDIA_UNAVAILABLE")) {
            return INSTALL_FAILED_MEDIA_UNAVAILABLE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_TIMEOUT")) {
            return INSTALL_FAILED_VERIFICATION_TIMEOUT;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_FAILURE")) {
            return INSTALL_FAILED_VERIFICATION_FAILURE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_PACKAGE_CHANGED")) {
            return INSTALL_FAILED_PACKAGE_CHANGED;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_UID_CHANGED")) {
            return INSTALL_FAILED_UID_CHANGED;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NOT_APK")) {
            return INSTALL_PARSE_FAILED_NOT_APK;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_MANIFEST")) {
            return INSTALL_PARSE_FAILED_BAD_MANIFEST;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION")) {
            return INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NO_CERTIFICATES")) {
            return INSTALL_PARSE_FAILED_NO_CERTIFICATES;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES")) {
            return INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING")) {
            return INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME")) {
            return INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID")) {
            return INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_MALFORMED")) {
            return INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_EMPTY")) {
            return INSTALL_PARSE_FAILED_MANIFEST_EMPTY;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INTERNAL_ERROR")) {
            return INSTALL_FAILED_INTERNAL_ERROR;
        }
        return INSTALL_FAILED_OTHER;
    }

    /**
     * uninstall according conditions
     * <ul>
     * <li>if system application or rooted, see
     * {@link #uninstallSilent(Context, String)}</li>
     * <li>else see {@link #uninstallNormal(Context, String)}</li>
     * </ul>
     *
     * @param context the environment of context
     * @param packageName package name of app
     * @return whether package name is empty
     */
    public static int uninstall(Context context, String packageName) {
        if (PackageUtils.isSystemApplication(context) || ShellUtils.checkRootPermission()) {
            return uninstallSilent(context, packageName);
        }
        return uninstallNormal(context, packageName) ? DELETE_SUCCEEDED : DELETE_FAILED_INVALID_PACKAGE;
    }

    /**
     * uninstall package normal by system intent
     *
     * @param context the environment of context
     * @param packageName package name of app
     * @return whether package name is empty
     */
    public static boolean uninstallNormal(Context context, String packageName) {
        if (packageName == null || packageName.length() == 0) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_DELETE,
                Uri.parse(new StringBuilder(32).append("package:").append(packageName).toString()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

    /**
     * uninstall package and clear data of app silent by root
     *
     * @param context the environment of context
     * @param packageName package name of app
     * @return
     * @see #uninstallSilent(Context, String, boolean)
     */
    public static int uninstallSilent(Context context, String packageName) {
        return uninstallSilent(context, packageName, true);
    }

    /**
     * uninstall package silent by root
     * <ul>
     * <strong>Attentions:</strong>
     * <li>Don't call this on the ui thread, it may costs some times.</li>
     * <li>You should add <strong>android.permission.DELETE_PACKAGES</strong> in
     * manifest, so no need to request root permission, if you are system app.</li>
     * </ul>
     *
     * @param context the environment of context
     * @param packageName package name of app
     * @param isKeepData whether keep the data and cache directories around after
     *            package removal
     * @return <ul>
     *         <li>{@link #DELETE_SUCCEEDED} means uninstall success</li>
     *         <li>{@link #DELETE_FAILED_INTERNAL_ERROR} means internal error</li>
     *         <li>{@link #DELETE_FAILED_INVALID_PACKAGE} means package name
     *         error</li>
     *         <li>{@link #DELETE_FAILED_PERMISSION_DENIED} means permission
     *         denied</li>
     */
    public static int uninstallSilent(Context context, String packageName, boolean isKeepData) {
        if (packageName == null || packageName.length() == 0) {
            return DELETE_FAILED_INVALID_PACKAGE;
        }
        /**
         * if context is system app, don't need root permission, but should add
         * <uses-permission android:name="android.permission.DELETE_PACKAGES" />
         * in manifest
         **/
        StringBuilder command = new StringBuilder()
                .append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall")
                .append(isKeepData ? " -k " : " ")
                .append(packageName.replace(" ", "\\ "));
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command.toString(), !isSystemApplication(context), true);
        if (commandResult.successMsg != null && (commandResult.successMsg.contains("Success") || commandResult.successMsg.contains("success"))) {
            return DELETE_SUCCEEDED;
        }
        Log.e(TAG, new StringBuilder().append("uninstallSilent successMsg:")
                .append(commandResult.successMsg).append(", ErrorMsg:")
                .append(commandResult.errorMsg).toString());
        if (commandResult.errorMsg == null) {
            return DELETE_FAILED_INTERNAL_ERROR;
        }
        if (commandResult.errorMsg.contains("Permission denied")) {
            return DELETE_FAILED_PERMISSION_DENIED;
        }
        return DELETE_FAILED_INTERNAL_ERROR;
    }

    /**
     * whether context is system application
     *
     * @param context the environment of context
     * @return true or false
     */
    public static boolean isSystemApplication(Context context) {
        if (context == null) {
            return false;
        }
        return isSystemApplication(context, context.getPackageName());
    }

    /**
     * whether packageName is system application
     *
     * @param context the environment of context
     * @param packageName package name of app
     * @return true or false
     */
    public static boolean isSystemApplication(Context context, String packageName) {
        if (context == null) {
            return false;
        }
        return isSystemApplication(context.getPackageManager(), packageName);
    }

    /**
     * whether packageName is system application
     *
     * @param packageManager package manager instance
     * @param packageName package name of app
     * @return <ul>
     *         <li>if packageManager is null, return false</li>
     *         <li>if package name is null or is empty, return false</li>
     *         <li>if package name not exit, return false</li>
     *         <li>if package name exit, but not system app, return false</li>
     *         <li>else return true</li>
     *         </ul>
     */
    public static boolean isSystemApplication(PackageManager packageManager, String packageName) {
        if (packageManager == null || packageName == null || packageName.length() == 0) {
            return false;
        }
        try {
            ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
            return (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * whether the app whose package's name is packageName is on the top of the
     * stack
     * <ul>
     * <strong>Attentions:</strong>
     * <li>You should add <strong>android.permission.GET_TASKS</strong> in
     * manifest</li>
     * </ul>
     *
     * @param context the environment of context
     * @param packageName package name of app
     * @return if params error or task stack is null, return null, otherwise
     *         return whether the app is on the top of stack
     */
    public static boolean isTopActivity(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo == null || tasksInfo.size() == 0) {
            return false;
        }
        try {
            ActivityManager.RunningTaskInfo taskInfo = tasksInfo.get(0);
            ComponentName topActivity = taskInfo.topActivity;
            if (topActivity == null) {
                return false;
            }
            return packageName.equals(topActivity.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * get system install location<br/>
     * can be set by System Menu Setting->Storage->Prefered install location
     *
     * @return system install location
     */
    public static int getInstallLocation() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm get-install-location", false, true);
        if (commandResult.result == 0 && commandResult.successMsg != null && commandResult.successMsg.length() > 0) {
            try {
                int location = Integer.parseInt(commandResult.successMsg.substring(0, 1));
                switch (location) {
                    case APP_INSTALL_INTERNAL:
                        return APP_INSTALL_INTERNAL;
                    case APP_INSTALL_EXTERNAL:
                        return APP_INSTALL_EXTERNAL;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Log.e(TAG, "pm get-install-location error");
            }
        }
        return APP_INSTALL_AUTO;
    }

    /**
     * get params for pm install location
     *
     * @return String
     */
    private static String getInstallLocationParams() {
        int location = getInstallLocation();
        switch (location) {
            case APP_INSTALL_INTERNAL:
                return "-f";
            case APP_INSTALL_EXTERNAL:
                return "-s";
        }
        return "";
    }

    /**
     * start InstalledAppDetails Activity
     * @param context the environment of context
     * @param packageName package name of app
     */
    public static void startInstalledAppDetails(Context context, String packageName) {
        Intent intent = new Intent();
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 9) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName, null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra((sdkVersion == 8 ? "pkg" : "com.android.settings.ApplicationPkgName"), packageName);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context 上下文对象
     * @param packageName 应用包名
     * @return returns true or false
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        List<String> packageNameList = new ArrayList<>();
        for (int i = 0; i < packageInfoList.size(); i++) {
            String packName = packageInfoList.get(i).packageName;
            packageNameList.add(packName);
        }
        // 判断包名列表中是否有目标程序的包名
        return packageNameList.contains(packageName);
    }

    public static boolean isAppInstalled2(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return (packageInfo != null);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取配置文件标签中的值
     * @param context 上下文对象
     * @param metaKey 标签中的键
     * @return 配置文件标签中的值
     */
    public static String getMetaValue(Context context, String metaKey) {
        if (context == null || TextUtils.isEmpty(metaKey)) {
            return null;
        }
        String metaValue = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            metaValue = appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaValue;
    }

    /**
     * get name of app
     * @param context the environment of context
     * @return name of app
     */
    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return context.getApplicationInfo().loadLabel(packageManager).toString();
    }

    /**
     * get version code of app
     * @param context the environment of context
     * @return version code of app
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get version name of app
     * @param context the environment of context
     * @return version name of app
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            // get package name of current class，0 which means the package info
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取应用签名信息
     *
     * @param context     上下文对象
     * @param packageName 应用包名
     * @return 应用签名字符串
     */
    public static String getSignatureInfo(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iterator = packageInfoList.iterator();
        String itemPackageName;
        Signature[] signatureArray = null;
        while (iterator.hasNext()) {
            PackageInfo packageInfo = iterator.next();
            itemPackageName = packageInfo.packageName;
            // 判断包名是否相同
            if (packageName.equals(itemPackageName)) {
                signatureArray = packageInfo.signatures;
            }
        }
        // 判断签名数组是否为空
        if (signatureArray != null) {
            StringBuilder builder = new StringBuilder();
            for (Signature itemSignature : signatureArray) {
                builder.append(itemSignature);
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 获取设备安装的应用程序列表
     * @param context 上下文
     * @param flag 选项标识
     * @return 安装的应用程序列表
     */
    public static List<ApplicationInfo> getInstalledApplications(Context context, int flag) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getInstalledApplications(flag);
    }

    public static boolean canRequestPackageInstalls(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.canRequestPackageInstalls();
        }
        return true;
    }
}
