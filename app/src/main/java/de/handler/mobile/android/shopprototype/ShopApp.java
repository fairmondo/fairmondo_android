package de.handler.mobile.android.shopprototype;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.EApplication;

import de.handler.mobile.android.shopprototype.database.DaoMaster;
import de.handler.mobile.android.shopprototype.database.DaoSession;
import de.handler.mobile.android.shopprototype.util.CustomImageCache;

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
        this.initDatabase("fairmondo-db");
    }

    private CustomImageCache imageCache;
    private ImageLoader imageLoader;
    private DaoSession daoSession;

    private boolean isAppConnected = false;

    /**
     * methods for initialization
     * on app start
     */
    private void initCache() {
        imageCache = new CustomImageCache();
    }

    private void initImageLoader() {
        imageLoader = new ImageLoader(CustomImageCache.newRequestQueue(this), imageCache);
    }

    private void initDatabase(String databaseName) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, databaseName, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return  daoSession;
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
