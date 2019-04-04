package com.lyric.support.net.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * response callback
 *
 * @author lyricgan
 */
public abstract class ResponseCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            T responseBody = response.body();
            if (responseBody == null) {
                onError(call, buildError(ResponseError.ERROR_DATA_EXCEPTION, "Response is null"));
                return;
            }
            onResponse(call, response.body());
        } else {
            onError(call, buildError(response.code(), response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError(call, buildError(ResponseError.ERROR_REQUEST_FAILED, t.getMessage()));
    }

    private ResponseError buildError(int errorCode, String errorMessage) {
        ResponseError responseError = new ResponseError();
        responseError.setCode(errorCode);
        responseError.setMessage(errorMessage);
        return responseError;
    }

    public abstract void onResponse(Call<T> call, T response);

    public abstract void onError(Call<T> call, ResponseError error);
}
