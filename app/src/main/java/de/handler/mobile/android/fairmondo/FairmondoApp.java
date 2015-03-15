package de.handler.mobile.android.fairmondo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.EApplication;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;

import java.util.List;

import de.handler.mobile.android.fairmondo.datalayer.businessobject.Cart;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.Product;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.datalayer.datasource.SearchSuggestionProvider;
import de.handler.mobile.android.fairmondo.datalayer.datasource.database.DaoMaster;
import de.handler.mobile.android.fairmondo.datalayer.datasource.database.DaoSession;
import de.handler.mobile.android.fairmondo.ui.views.CustomImageCache;

/**
 * Application Object.
 * Used to hold application-wide instances of objects
 */
@EApplication
public class FairmondoApp extends Application {
    private ModelMapper modelMapper;
    private CustomImageCache imageCache;
    private ImageLoader imageLoader;
    private DaoSession daoSession;
    private FairmondoCategory lastCategory;
    private String cookie;
    private List<Product> products;
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
     * initiate the modelmapper.
     * It maps data transfer objects (used for the pure network communication)
     * to business objects (used in the ui).
     */
    private void initModelMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
    }

    /**
     * initiate the image cache used for Volley.
     * The image cache implements volley's image cache and extends the Android's LRU cache
     */
    private void initCache() {
        imageCache = new CustomImageCache();
    }

    /**
     * initiate the image loader used by Volley for image retrieval.
     */
    private void initImageLoader() {
        imageLoader = new ImageLoader(CustomImageCache.newRequestQueue(this), imageCache);
    }

    /**
     * initiate the database connection.
     * Greendao which is used for connecting to a
     * database needs the global dao session instance.
     */
    private void initDatabase(String databaseName) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, databaseName, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        // Init the dao session in the content provider
        SearchSuggestionProvider.daoSession = daoSession;
    }

    /**
     * Returns the dao session for greendao.
     * @return the session used for the database connection
     */
    public DaoSession getDaoSession() {
        return  daoSession;
    }

    /**
     * Returns the image loader Volley uses to retrieve images from a url.
     * @return the image loader for Volley
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * Returns whether the device is connected to the internet.
     * @return the app's network connection state
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Sets if the device is currently connected to the internet.
     * @param isAppConnected sets the network connection state of the app
     */
    public void setConnected(boolean isAppConnected) {
        this.isConnected = isAppConnected;
    }

    /**
     * Returns the last Category the user selected in the ui.
     * @return the last Category the user selected in the ui
     */
    public FairmondoCategory getLastCategory() {
        return lastCategory;
    }

    /**
     * Sets the last Category the user selected in the ui.
     * @param lastCategory the last Category the user selected in the ui
     */
    public void setLastCategory(FairmondoCategory lastCategory) {
        this.lastCategory = lastCategory;
    }

    /**
     * Returns the cookie which identifies the cart in the application.
     * @return the cookie of a user's cart
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * Sets the cookie which identifies the cart in the application.
     * @param cookie the cookie of a user's cart
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * Returns the cart a user uses in the app.
     * @return the current cart instance
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Sets the cart a user uses in the app.
     * @param cart the current cart instance
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Returns the modelmapper instance to map dtos to bos.
     * @return the current modelmapper instance
     */
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * Returns the products a user selected.
     * @return the products a user selected
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the products a user selected.
     * @param products the products a user selected
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
