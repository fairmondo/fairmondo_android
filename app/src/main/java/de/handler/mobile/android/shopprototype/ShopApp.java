package de.handler.mobile.android.shopprototype;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.EApplication;

import de.handler.mobile.android.shopprototype.datasource.CategoryContentProvider;
import de.handler.mobile.android.shopprototype.datasource.SearchSuggestionContentProvider;
import de.handler.mobile.android.shopprototype.datasource.database.DaoMaster;
import de.handler.mobile.android.shopprototype.datasource.database.DaoSession;
import de.handler.mobile.android.shopprototype.rest.json.model.Cart;
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
    private int lastCategory;
    private String cookie;

    private Cart cart;

    private boolean isConnected = false;

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

        // Init the dao session in the content provider
        CategoryContentProvider.daoSession = daoSession;
        SearchSuggestionContentProvider.daoSession = daoSession;
    }

    public DaoSession getDaoSession() {
        return  daoSession;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isAppConnected) {
        this.isConnected = isAppConnected;
    }

    public int getLastCategory() {
        return lastCategory;
    }

    public void setLastCategory(int lastCategory) {
        this.lastCategory = lastCategory;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
