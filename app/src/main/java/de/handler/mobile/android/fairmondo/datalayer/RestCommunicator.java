package de.handler.mobile.android.fairmondo.datalayer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.Cart;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.datalayer.interfaces.OnCartChangeListener;
import de.handler.mobile.android.fairmondo.datalayer.interfaces.OnCategoriesListener;
import de.handler.mobile.android.fairmondo.datalayer.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.fairmondo.datalayer.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.networklayer.rest.Articles;
import de.handler.mobile.android.fairmondo.networklayer.rest.FairmondoRestService;
import de.handler.mobile.android.fairmondo.networklayer.rest.RestServiceErrorHandler;
import de.handler.mobile.android.fairmondo.networklayer.rest.dto.Product;

/**
 * Encapsulates all communication with the server
 */
@EBean
public class RestCommunicator {
    @Bean
    RestServiceErrorHandler errorHandler;

    @RestService
    FairmondoRestService restService;

    @App
    FairmondoApp app;

    private OnSearchResultListener productListener;
    private OnCategoriesListener categoriesListener;
    private OnDetailedProductListener detailedProductListener;
    private OnCartChangeListener cartChangeListener;
    private final Context context;

    public RestCommunicator(Context context) {
        this.context = context;
    }

    @AfterInject
    public void initRestService() {
        errorHandler.setContext(context);
        restService.setRestErrorHandler(errorHandler);
    }

    public void setProductListener(OnSearchResultListener productListener) {
        this.productListener = productListener;
        errorHandler.setListener(productListener);
    }

    public void setCategoriesListener(OnCategoriesListener categoriesListener) {
        this.categoriesListener = categoriesListener;
    }

    public void setDetailedProductListener(OnDetailedProductListener detailedProductListener) {
        this.detailedProductListener = detailedProductListener;
    }

    public void setCartChangeListener(OnCartChangeListener cartChangeListener) {
        this.cartChangeListener = cartChangeListener;
    }

    @Background
    public void getProduct(String searchString) {
        Articles articles = restService.getProducts(searchString);
        Type listType = new TypeToken<List<de.handler.mobile.android.fairmondo.datalayer.businessobject.Product>>() {}.getType();
        if (articles == null || articles.articles == null) {
            productListener.onProductsSearchResponse(null);
        } else {
            List<de.handler.mobile.android.fairmondo.datalayer.businessobject.Product> products = app.getModelMapper().map(articles.articles, listType);
            productListener.onProductsSearchResponse(products);
        }
    }

    @Background
    public void getProduct(String searchString, int categoryId) {
        productListener.showProgressBar();
        Articles articles = restService.getProducts(searchString, categoryId);
        if (articles == null || articles.articles == null || articles.articles.length < 1) {
            productListener.onProductsSearchResponse(null);
            Log.e(getClass().getCanonicalName(), " getProducts: articles are null");
        } else {
            Type listType = new TypeToken<List<de.handler.mobile.android.fairmondo.datalayer.businessobject.Product>>() {}.getType();
            List<de.handler.mobile.android.fairmondo.datalayer.businessobject.Product> products = app.getModelMapper().map(articles.articles, listType);
            productListener.onProductsSearchResponse(products);
        }
    }

    @Background
    public void getDetailedProduct(String url) {
        Product article = restService.getDetailedProduct(url);
        de.handler.mobile.android.fairmondo.datalayer.businessobject.Product product = app.getModelMapper().map(article, de.handler.mobile.android.fairmondo.datalayer.businessobject.Product.class);
        detailedProductListener.onDetailedProductResponse(product);
    }

    @Background(id = "cancellable_task")
    public void getCategories() {
        Type listType = new TypeToken<List<FairmondoCategory>>() {}.getType();
        List<de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article.FairmondoCategory> dtoCategories = restService.getCategories();
        List<FairmondoCategory> categories = app.getModelMapper().map(dtoCategories, listType);
        categoriesListener.onCategoriesResponse(categories);
    }

    @Background(id = "cancellable_task")
    public void getSubCategories(int id) {
        Type listType = new TypeToken<List<FairmondoCategory>>() {}.getType();
        List<de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article.FairmondoCategory> dtoCategories = restService.getSubCategories(id);
        List<FairmondoCategory> categories = app.getModelMapper().map(dtoCategories, listType);
        categoriesListener.onSubCategoriesResponse(categories);
    }

    @Background(id = "cancellable_task")
    public void addToCard(int productId) {
        restService.setCookie("cart", app.getCookie());
        Cart cart = app.getModelMapper().map(restService.addProductToCart(productId, 1), Cart.class);
        String cookie = restService.getCookie("cart");

        if (cart == null || cart.getCartId() == null) {
            this.makeToast();
        } else {
            app.setCookie(cookie);
            app.setCart(cart);
            cartChangeListener.onCartChanged(cart);
        }
    }

    @UiThread
    public void makeToast() {
        Toast.makeText(context, context.getString(R.string.item_amount_too_big), Toast.LENGTH_SHORT).show();
    }
}
