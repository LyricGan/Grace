package com.lyric.grace.api.interceptor;

import android.os.Build;
import android.text.TextUtils;

import com.lyric.grace.Grace;
import com.lyric.grace.utils.PackageUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author <a href="mailto:ganyu@medlinker.com">ganyu</a>
 * @version 1.0
 * @description 默认网络请求参数拦截器
 * @time 2016/10/24 15:50
 */
public class ParamsInterceptor implements Interceptor {
    // 客户端应用版本号
    private static String mVersionName;
    // 手机设备号
    private static String mDeviceId;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = addDefaultParams(chain.request());
        return chain.proceed(request);
    }

    /**
     * 添加默认请求参数
     * sess 加密的会话id
     * sys_p 手机系统类型(platform)，如果为a，表示android，如果是i，表示ios
     * sys_v 客户端的系统版本(version)
     * cli_v 客户端安装的应用的版本
     * sys_m 手机的型号(model)
     * sys_d 手机设备号
     * @param request Request
     * @return 添加参数后的Request
     */
    private Request addDefaultParams(Request request) {
        HttpUrl httpUrl = request.url().newBuilder()
                .addQueryParameter("sys_p", "a")
                .addQueryParameter("sys_v", Build.VERSION.RELEASE)
                .addQueryParameter("cli_v", getVersionName())
                .addQueryParameter("sys_m", Build.MODEL)
                .addQueryParameter("sys_d", getDeviceId())
                .build();
        return request.newBuilder().url(httpUrl).build();
    }

    private String getVersionName() {
        if (TextUtils.isEmpty(mVersionName)) {
            mVersionName = PackageUtils.getAppVersionName(Grace.getContext());
        }
        return mVersionName;
    }

    private String getDeviceId() {
        if (TextUtils.isEmpty(mDeviceId)) {
            mDeviceId = PackageUtils.getDeviceId(Grace.getContext());
        }
        return mDeviceId;
    }
}
