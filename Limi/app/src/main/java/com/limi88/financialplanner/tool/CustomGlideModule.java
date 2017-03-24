package com.limi88.financialplanner.tool;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Administrator on 2016/7/19.
 */

public class CustomGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskLruCacheFactory(DiskLruCacheFactory.DEFAULT_DISK_CACHE_DIR, DiskLruCacheFactory.DEFAULT_DISK_CACHE_SIZE))
                .setMemoryCache(new LruResourceCache(20 * 1024))
                .setBitmapPool(new LruBitmapPool(300));

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
