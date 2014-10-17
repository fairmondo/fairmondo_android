package de.handler.mobile.android.shopprototype.utils;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;

/**
 * Custom Image Loader for Volley Library
 */
public class LocalImageLoader extends ImageLoader {
    public LocalImageLoader(Context mContext) {
        super(de.handler.mobile.android.shopprototype.utils.CustomImageCache.newRequestQueue(mContext),
                new de.handler.mobile.android.shopprototype.utils.CustomImageCache());
    }
}
