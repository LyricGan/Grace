package com.lyricgan.retrofit.okhttp;

import java.io.IOException;

import okhttp3.ResponseBody;

public abstract class OnStringCallback extends HttpResponseCallback<String> {

    @Override
    public String parseResponse(ResponseBody responseBody) {
        try {
            return responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
