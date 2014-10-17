package de.handler.mobile.android.shopprototype;

import android.app.Application;
import de.handler.mobile.android.shopprototype.utils.CustomImageCache;

import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.EApplication;

/**
 * Application Object
 */
@EApplication
public class ShopApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        this.initCache();
        this.initImageLoader();
    }

    private CustomImageCache imageCache;
    private ImageLoader imageLoader;

    private boolean isAppConnected = false;

    private void initCache() {
        imageCache = new CustomImageCache();
    }

    private void initImageLoader() {
        imageLoader = new ImageLoader(CustomImageCache.newRequestQueue(this), imageCache);
    }


    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public boolean isAppConnected() {
        return isAppConnected;
    }

    public void setAppConnected(boolean isAppConnected) {
        this.isAppConnected = isAppConnected;
    }

}
