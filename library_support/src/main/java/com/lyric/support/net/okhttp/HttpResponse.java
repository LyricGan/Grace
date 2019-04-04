package com.lyric.support.net.okhttp;

import okhttp3.Response;

/**
 * 网络响应包装类
 *
 * @author lyricgan
 */
public class HttpResponse {
    private Response response;

    public HttpResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
