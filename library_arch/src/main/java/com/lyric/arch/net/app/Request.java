package com.lyric.arch.net.app;

import android.os.Message;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author lyricgan
 * @time 2016/6/22 13:40
 */
public class Request<T> implements HttpHandler.OnMessageCallback {
    private static final int MESSAGE_SUCCESS = 0x01 << 2;
    private static final int MESSAGE_FAILED = 0x02 << 2;
    private Method mMethod;
    private String mUrl;
    private Map<String, String> mParams;
    private Map<String, File> mFileParams;
    private ResponseCallback<T> mCallback;
    private HttpHandler mHandler = new HttpHandler<>(this);
    private Type mType;
    private Dispatcher mDispatcher;

    public Request(Method method, String url, Map<String, String> params, Type type, ResponseCallback<T> callback) {
        this(method, url, params, null, type, callback);
    }

    public Request(Method method, String url, Map<String, String> params, Map<String, File> fileParams, Type type, ResponseCallback<T> callback) {
        this.mMethod = method;
        this.mUrl = url;
        this.mParams = params;
        this.mFileParams = fileParams;
        this.mCallback = callback;
        this.mType = type;
        this.mHandler.setCallback(this);
    }

    public ResponseEntity executeSync() throws InterruptedException {
        return executeSync(true);
    }

    public ResponseEntity executeSync(boolean isRefresh) throws InterruptedException {
        ResponseEntity responseEntity;
        if (Method.GET == mMethod) {
            responseEntity = HttpUtils.get(mUrl, mParams, isRefresh);
        } else if (Method.POST == mMethod) {
            responseEntity = HttpUtils.post(mUrl, mParams, isRefresh);
        } else if (Method.UPLOAD == mMethod) {
            responseEntity = HttpUtils.upload(mUrl, mParams, mFileParams);
        } else {
            throw new IllegalArgumentException("Request method error.");
        }
        return responseEntity;
    }

    public void execute(final boolean isRefresh) {
        mDispatcher = new Dispatcher(this, isRefresh);
        mDispatcher.start();
    }

    public void processResponse(ResponseEntity responseEntity) {
        if (responseEntity.isSuccess()) {
            String response = responseEntity.getResponse();
            T result = GsonConverter.getInstance().convert(response, mType);
            if (result != null) {
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS);
                msg.obj = result;
                mHandler.sendMessage(msg);
            } else {
                Message msg = mHandler.obtainMessage(MESSAGE_FAILED);
                msg.obj = responseEntity;
                mHandler.sendMessage(msg);
            }
        } else {
            Message msg = mHandler.obtainMessage(MESSAGE_FAILED);
            msg.obj = responseEntity;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void callback(Message msg) {
        if (MESSAGE_SUCCESS == msg.what) {// 请求成功
            T object = (T) msg.obj;
            mCallback.onSuccess(object);
        } else if (MESSAGE_FAILED == msg.what) {// 请求失败
            ResponseEntity responseEntity = (ResponseEntity) msg.obj;
            mCallback.onFailed(getResponseError(responseEntity));
        }
    }

    private ResponseError getResponseError(ResponseEntity responseEntity) {
        ResponseError responseError = new ResponseError();
        responseError.setUrl(responseEntity.getUrl());
        responseError.setParams(responseEntity.getParams());
        responseError.setCode(responseEntity.getResponseCode());
        responseError.setMessage(responseEntity.getResponse());

        return responseError;
    }

    public void cancel() {
        if (mDispatcher != null) {
            mDispatcher.cancel();
        }
    }
}
