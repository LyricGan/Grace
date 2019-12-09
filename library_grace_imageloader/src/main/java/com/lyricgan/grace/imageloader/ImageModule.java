package com.lyricgan.grace.imageloader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

/**
 * 图片加载自定义模块，需要在AndroidManifest文件注册
 * @author Lyric Gan
 * @since 2019-12-08
 */
@GlideModule
public class ImageModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        RequestOptions requestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .format(DecodeFormat.PREFER_RGB_565);
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setBitmapPoolScreens(3)
                .build();
        builder.setDefaultRequestOptions(requestOptions)
                .setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()))
                .setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()))
                .setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context))
                .setLogLevel(Log.DEBUG);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // 注册使用统一的OkHttpClient加载图片
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
