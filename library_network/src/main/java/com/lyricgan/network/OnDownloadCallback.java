package com.lyricgan.network;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 文件下载回调
 * @author Lyric Gan
 */
public abstract class OnDownloadCallback implements ResponseCallback {
    private final String saveFilePath;

    public OnDownloadCallback(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

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
            String saveFilePath = this.saveFilePath;
            if (TextUtils.isEmpty(saveFilePath)) {
                handleFailed(call, new IOException("File path empty, response code is " + response.code()));
                return;
            }
            BufferedInputStream inputStream = null;
            BufferedOutputStream outputStream = null;
            try {
                long contentLength = responseBody.contentLength();
                if (contentLength > 0) {
                    inputStream = new BufferedInputStream(responseBody.byteStream());
                    outputStream = new BufferedOutputStream(new FileOutputStream(saveFilePath));
                    byte[] buffer = new byte[1024];
                    int len;
                    long current = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                        current += len;
                        float progress = current * 1.0f / contentLength;
                        handleProgressChanged(call, progress, contentLength);
                    }
                    outputStream.flush();
                    handleSuccess(call, saveFilePath);
                } else {
                    handleFailed(call, new IOException("Response content length is " + contentLength + ", response code is " + response.code()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                handleFailed(call, e);
            } finally {
                responseBody.close();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            handleFailed(call, new IOException("Request failed, response code is " + response.code()));
        }
    }

    private void handleFailed(Call call, IOException e) {
        HttpManager.getInstance().getMainHandler().post(() -> onFailed(call, e));
    }

    private void handleSuccess(Call call, String saveFilePath) {
        HttpManager.getInstance().getMainHandler().post(() -> onSuccess(call, saveFilePath));
    }

    private void handleProgressChanged(Call call, float progress, long contentLength) {
        HttpManager.getInstance().getMainHandler().post(() -> onProgressChanged(call, progress, contentLength));
    }

    public abstract void onFailed(Call call, IOException e);

    public abstract void onSuccess(Call call, String saveFilePath);

    public abstract void onProgressChanged(Call call, float progress, long contentLength);
}
