package de.handler.mobile.android.fairmondo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.EApplication;

import de.handler.mobile.android.fairmondo.datasource.SearchSuggestionProvider;
import de.handler.mobile.android.fairmondo.datasource.database.Category;
import de.handler.mobile.android.fairmondo.datasource.database.DaoMaster;
import de.handler.mobile.android.fairmondo.datasource.database.DaoSession;
import de.handler.mobile.android.fairmondo.rest.json.model.Cart;
import de.handler.mobile.android.fairmondo.ui.views.CustomImageCache;

/**
 * Application Object
 */
@EApplication
public class FairmondoApp extends Application {

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
    private Category lastCategory;
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
        SearchSuggestionProvider.daoSession = daoSession;
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

    public Category getLastCategory() {
        return lastCategory;
    }

    public void setLastCategory(Category lastCategory) {
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
