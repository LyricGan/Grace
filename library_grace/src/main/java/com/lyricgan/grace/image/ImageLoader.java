package com.lyricgan.grace.image;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

import java.io.File;

/**
 * 图片加载类
 * @author Lyric Gan
 * @since 2019-12-08
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

    public void load(ImageView view, Object objUrl, int placeholderId, ImageView.ScaleType scaleType) {
        load(view, objUrl, placeholderId, scaleType, null);
    }

    public void load(ImageView view, Object objUrl, int placeholderId, ImageView.ScaleType scaleType, RequestListener<?> listener) {
        ImageLoaderOptions options = ImageLoaderOptions.getDefaultConfig(placeholderId).transform(scaleType);
        load(view, objUrl, options, listener);
    }

    public void load(ImageView view, Object objUrl, @NonNull ImageLoaderOptions options, RequestListener listener) {
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

    public File getPhotoCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    public File getPhotoCacheDir(Context context, String cacheName) {
        return Glide.getPhotoCacheDir(context, cacheName);
    }
}
