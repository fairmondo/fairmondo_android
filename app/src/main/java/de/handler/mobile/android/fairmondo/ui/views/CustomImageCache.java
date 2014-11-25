package de.handler.mobile.android.fairmondo.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import java.io.File;

/**
 * Stores already downloaded bitmaps in a memory cache that they do not have to be downloaded again during session
 */
public class CustomImageCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    // Default maximum disk usage in bytes
    private static final int DEFAULT_DISK_USAGE_BYTES = 25 * 1024 * 1024;

    // Default cache folder name
    private static final String DEFAULT_CACHE_DIR = "photos";

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public CustomImageCache(int maxSize) {
        super(maxSize);
    }

    public CustomImageCache() {
        this(getDefaultLruCacheSize());
    }

    public static int getDefaultLruCacheSize() {
        final int maxMemory =
                (int) (Runtime.getRuntime().maxMemory() / 1024);

        return maxMemory / 8;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }


    // Change standard Volley RequestQueue which is memory based to a disk based solution
    public static RequestQueue newRequestQueue(Context context) {
        // define cache folder
        File rootCache = context.getExternalCacheDir();
        if (rootCache == null) {
            Log.d("MEMORY_CACHE: ", "Can't find External Cache Dir, "
                    + "switching to application specific cache directory");
            rootCache = context.getCacheDir();
        }

        File cacheDir = new File(rootCache, DEFAULT_CACHE_DIR);
        cacheDir.mkdirs();

        HttpStack stack = new HurlStack();
        Network network = new BasicNetwork(stack);
        DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, DEFAULT_DISK_USAGE_BYTES);
        RequestQueue queue = new RequestQueue(diskBasedCache, network);
        queue.start();

        return queue;
    }
}