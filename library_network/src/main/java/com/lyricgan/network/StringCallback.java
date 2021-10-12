package com.lyricgan.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 网络请求回调接口
 * @author Lyric Gan
 */
public abstract class StringCallback implements ResponseCallback {

    @Override
    public void onFailure(Call call, IOException e) {
        handleFailed(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (call.isCanceled()) {
            handleFailed(call, new IOException("Request canceled"));
            return;
        }
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                handleFailed(call, new IOException("Response content empty, response code is " + response.code()));
                return;
            }
            try {
                String content = responseBody.string();
                onSuccess(new HttpCall(call), content);
            } catch (IOException e) {
                e.printStackTrace();
                handleFailed(call, e);
            } finally {
                responseBody.close();
            }
        } else {
            handleFailed(call, new IOException("Request failed, response code is " + response.code()));
        }
    }

    protected void handleFailed(Call call, IOException e) {
        HttpManager.getInstance().getMainHandler().post(() -> onFailed(new HttpCall(call), e));
    }

    /**
     * 回调在主线程
     */
    public abstract void onFailed(HttpCall call, IOException e);

    /**
     * 回调在子线程
     */
    public abstract void onSuccess(HttpCall call, String model);
}
