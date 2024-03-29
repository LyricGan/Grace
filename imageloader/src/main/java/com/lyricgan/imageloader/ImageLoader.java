package com.lyricgan.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

/**
 * 图片加载类
 * @author Lyric Gan
 */
public class ImageLoader {

    private ImageLoader() {
    }

    private static final class ImageLoaderHolder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }

    public static ImageLoader getInstance() {
        return ImageLoaderHolder.INSTANCE;
    }

    public void load(ImageView view, Object objUrl, int placeholderId, ImageView.ScaleType scaleType, RequestListener<Bitmap> listener) {
        ImageLoaderOptions options = ImageLoaderOptions.getDefaultConfig(placeholderId).transform(scaleType);
        load(view, objUrl, options, listener);
    }

    public void load(ImageView view, Object objUrl, @NonNull ImageLoaderOptions options, RequestListener<Bitmap> listener) {
        if (view == null || objUrl == null) {
            return;
        }
        try {
            Glide.with(view)
                    .asBitmap()
                    .load(objUrl)
                    .apply(options.getRequestOptions())
                    .listener(listener)
                    .into(view);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }
}
