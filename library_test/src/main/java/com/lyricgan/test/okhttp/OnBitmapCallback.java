package com.lyricgan.test.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;

public abstract class OnBitmapCallback extends HttpResponseCallback<Bitmap> {

    @Override
    public Bitmap parseResponse(ResponseBody responseBody) {
        InputStream inputStream = null;
        try {
            inputStream = responseBody.byteStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.closeQuietly(inputStream);
        }
        return null;
    }
}
