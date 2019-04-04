package com.lyric.support.net.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;

/**
 * Bitmap请求回调接口
 * @author lyricgan
 */
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
