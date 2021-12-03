package com.lyricgan.network;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 网络请求回调接口
 * @author Lyric Gan
 */
public abstract class OnResponseCallback<T extends HttpEntity> extends StringCallback {
    private Type modelType;

    public OnResponseCallback() {
        Type type = getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            return;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length <= 0) {
            return;
        }
        modelType = actualTypeArguments[0];
    }

    @Override
    public void onSuccess(HttpCall call, String content) {
        T model = convert(content, modelType);
        if (model != null) {
            handleSuccess(call, model);
        } else {
            handleFailed(call, new IOException("Response parse failed"));
        }
    }

    private void handleFailed(HttpCall call, IOException e) {
        HttpManager.getInstance().getMainHandler().post(() -> onFailed(call, e));
    }

    private void handleSuccess(HttpCall call, T model) {
        HttpManager.getInstance().getMainHandler().post(() -> onSuccess(call, model));
    }

    protected abstract T convert(String content, Type modelType);

    public abstract void onFailed(HttpCall call, IOException e);

    public abstract void onSuccess(HttpCall call, T entity);
}
