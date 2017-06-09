package com.example.guowei.volleyusedemo.volleyApi;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;


/**
 * Created by Guowei on 2017/6/9.
 */

class BitmapCache implements ImageLoader.ImageCache {
    LruCache<String,Bitmap> cache;
    private int max=10*1024*1024;
    public BitmapCache() {
        cache = new LruCache<String,Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String position) {
        return cache.get(position);
    }

    @Override
    public void putBitmap(String position, Bitmap bitmap) {
        cache.put(position,bitmap);

    }
}
