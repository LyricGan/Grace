package com.lyric.grace.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 请求响应回调
 * @author lyricgan
 * @date 2016/7/26 17:55
 */
public abstract class ResponseCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            T object = response.body();
            if (object == null) {
                error(call, "ServerException: data parse error or response body is null");
                return;
            }
            onResponse(call, object);
        } else {
            error(call, response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        error(call, t != null ? t.getMessage() : "");
    }

    private void error(Call<T> call, String errorMessage) {
        onError(call, errorMessage);
    }

    public abstract void onResponse(Call<T> call, T response);

    public abstract void onError(Call<T> call, String errorMessage);
}
