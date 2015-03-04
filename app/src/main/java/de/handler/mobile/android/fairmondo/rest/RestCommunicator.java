package de.handler.mobile.android.fairmondo.rest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datasource.database.Category;
import de.handler.mobile.android.fairmondo.interfaces.OnCartChangeListener;
import de.handler.mobile.android.fairmondo.interfaces.OnCategoriesListener;
import de.handler.mobile.android.fairmondo.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.fairmondo.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.rest.json.Article;
import de.handler.mobile.android.fairmondo.rest.json.model.Cart;

/**
 * Encapsulates all communication with the server
 */
@EBean
public class RestCommunicator {

    @Bean
    RestServiceErrorHandler errorHandler;


    @org.androidannotations.annotations.rest.RestService
    FairmondoRestService restService;

    @App
    FairmondoApp app;


    private OnSearchResultListener productListener;
    private OnCategoriesListener categoriesListener;
    private OnDetailedProductListener detailedProductListener;
    private OnCartChangeListener cartChangeListener;
    private Context context;


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
        Articles articles = restService.getProduct(searchString);
        if (articles != null && articles.getArticles() != null) {
            productListener.onProductsSearchResponse(articles.getArticles());
        } else {
            productListener.onProductsSearchResponse(null);
        }
    }


    @Background
    public void getProduct(String searchString, int categoryId) {
        productListener.showProgressBar();
        Articles articles = restService.getProduct(searchString, categoryId);
        if (articles != null
                && articles.getArticles() != null
                && articles.getArticles().size() > 0) {

            Log.d(this.getClass().getCanonicalName(), String.valueOf(articles.getArticles().size()));
            productListener.onProductsSearchResponse(articles.getArticles());
        } else {
            productListener.onProductsSearchResponse(null);
        }
    }


    @Background
    public void getDetailedProduct(String slug) {
        Article article = restService.getDetailedProduct(slug);
        detailedProductListener.onDetailedProductResponse(article);
    }


    @Background(id = "cancellable_task")
    public void getCategories() {
        ArrayList<Category> categories = restService.getCategories();
        categoriesListener.onCategoriesResponse(categories);
    }

    @Background(id = "cancellable_task")
    public void getSubCategories(int id) {
        ArrayList<Category> categories = restService.getSubCategories(id);
        categoriesListener.onSubCategoriesResponse(categories);
    }

    @Background(id = "cancellable_task")
    public void addToCard(int productId) {
        restService.setCookie("cart", app.getCookie());

        Cart cart = restService.addProductToCart(productId, 1);
        String cookie = restService.getCookie("cart");

        if (cart != null && cart.getCart_id() != 0) {
            app.setCookie(cookie);
            app.setCart(cart);
            cartChangeListener.onCartChanged(cart);
        } else {
            this.makeToast();
        }
    }

    @UiThread
    public void makeToast() {
        Toast.makeText(context, context.getString(R.string.item_amount_too_big), Toast.LENGTH_SHORT).show();
    }
}
