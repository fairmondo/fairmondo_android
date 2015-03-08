package de.handler.mobile.android.fairmondo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.EApplication;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;

import de.handler.mobile.android.fairmondo.datalayer.businessobject.Cart;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.datalayer.datasource.SearchSuggestionProvider;
import de.handler.mobile.android.fairmondo.datalayer.datasource.database.DaoMaster;
import de.handler.mobile.android.fairmondo.datalayer.datasource.database.DaoSession;
import de.handler.mobile.android.fairmondo.ui.views.CustomImageCache;

/**
 * Application Object
 */
@EApplication
public class FairmondoApp extends Application {

    private ModelMapper modelMapper;
    private CustomImageCache imageCache;
    private ImageLoader imageLoader;
    private DaoSession daoSession;
    private FairmondoCategory lastCategory;
    private String cookie;

    private Cart cart;
    private boolean isConnected = false;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initCache();
        this.initImageLoader();
        this.initDatabase("fairmondo-db");
        this.initModelMapper();
    }

    /**
     * methods for initialization
     * on app start
     */
    private void initModelMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
    }

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

    public FairmondoCategory getLastCategory() {
        return lastCategory;
    }

    public void setLastCategory(FairmondoCategory lastCategory) {
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

    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}
