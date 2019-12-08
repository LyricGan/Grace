package com.lyricgan.grace.network.okhttp;

import java.io.IOException;

public interface HttpCallback {

    void onResponse(HttpRequest httpRequest, HttpResponse httpResponse);

    void onFailure(HttpRequest httpRequest, IOException e);

    void onCancel(HttpRequest httpRequest);
}
