package com.lyricgan.grace.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;

/**
 * 图片加载参数配置类
 * @author Lyric Gan
 * @since 2019-12-09
 */
public class ImageLoaderOptions {
    private RequestOptions requestOptions;

    public ImageLoaderOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
    }

    public RequestOptions getRequestOptions() {
        return requestOptions;
    }

    public void setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
    }

    public static ImageLoaderOptions getDefaultConfig(@DrawableRes int placeholderId) {
        RequestOptions requestOptions = RequestOptions.placeholderOf(placeholderId)
                .error(placeholderId)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .priority(Priority.HIGH)
                .centerCrop()
                .dontAnimate();
        return new ImageLoaderOptions(requestOptions);
    }

    public int getPlaceholderId() {
        return requestOptions.getPlaceholderId();
    }

    @SafeVarargs
    private final ImageLoaderOptions transform(@NonNull Transformation<Bitmap>... transformations) {
        setRequestOptions(requestOptions.transform(transformations));
        return this;
    }

    private Transformation<Bitmap> getScaleTransformation(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.FIT_CENTER) {
            return new FitCenter();
        } else if (scaleType == ImageView.ScaleType.CENTER_INSIDE) {
            return new CenterInside();
        } else {
            return new CenterCrop();
        }
    }

    public ImageLoaderOptions transform(ImageView.ScaleType scaleType) {
        return transform(scaleType, null);
    }

    public ImageLoaderOptions transform(ImageView.ScaleType scaleType, Transformation<Bitmap> transformation) {
        return transform(getScaleTransformation(scaleType), transformation);
    }
}
