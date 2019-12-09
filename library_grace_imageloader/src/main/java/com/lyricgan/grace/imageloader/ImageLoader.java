package com.lyricgan.grace.imageloader;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
        private static final ImageLoader IMAGE_LOADER = new ImageLoader();
    }

    public static ImageLoader getInstance() {
        return ImageLoaderHolder.IMAGE_LOADER;
    }

    public static void load(ImageView view, Object objUrl, int placeholderId) {
        load(view, objUrl, placeholderId, ImageView.ScaleType.CENTER_CROP);
    }

    public static void load(ImageView view, Object objUrl, int placeholderId, ImageView.ScaleType scaleType) {
        load(view, objUrl, placeholderId, scaleType, null);
    }

    public static void load(ImageView view, Object objUrl, int placeholderId, ImageView.ScaleType scaleType, ImageRequestListener<?> listener) {
        ImageLoaderOptions options = ImageLoaderOptions.getDefaultConfig(placeholderId).transform(scaleType);
        load(view, objUrl, options, listener);
    }

    public static void load(ImageView view, Object objUrl, @NonNull ImageLoaderOptions options, RequestListener listener) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (objUrl == null) {
            return;
        }
        try {
            RequestBuilder<?> builder = Glide.with(context).asBitmap();
            builder.load(objUrl)
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

    /**
     * 图片加载回调接口
     * @param <R> 资源类型
     */
    public static abstract class ImageRequestListener<R> implements RequestListener<R> {

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
            onLoadFailed(model, isFirstResource);
            return false;
        }

        @Override
        public boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
            onLoadSuccess(resource, model, isFirstResource);
            return false;
        }

        public abstract void onLoadFailed(Object model, boolean isFirstResource);

        public abstract void onLoadSuccess(R resource, Object model, boolean isFirstResource);
    }
}
