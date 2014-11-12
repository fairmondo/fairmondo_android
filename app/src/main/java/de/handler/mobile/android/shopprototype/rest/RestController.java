package de.handler.mobile.android.shopprototype.rest;

import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.datasource.database.Category;
import de.handler.mobile.android.shopprototype.interfaces.OnCartChangeListener;
import de.handler.mobile.android.shopprototype.interfaces.OnCategoriesListener;
import de.handler.mobile.android.shopprototype.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.rest.json.model.Cart;

/**
 * Encapsulates all communication with the server
 */
@EBean
public class RestController {

    @Bean
    RestServiceErrorHandler errorHandler;


    @RestService
    FairmondoRestService restService;

    @App
    ShopApp app;


    private OnSearchResultListener productListener;
    private OnCategoriesListener categoriesListener;
    private OnDetailedProductListener detailedProductListener;
    private OnCartChangeListener cartChangeListener;
    private Context mContext;


    public RestController(Context context) {
        mContext = context;
    }

    @AfterInject
    public void initRestService() {
        errorHandler.setContext(mContext);
        restService.setRestErrorHandler(errorHandler);
    }

    public void setProductListener(OnSearchResultListener productListener) {
        this.productListener = productListener;
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
        Articles articles = restService.getProduct(searchString, categoryId);
        if (articles != null && articles.getArticles() != null) {
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


    @Background
    public void getCategories() {
        ArrayList<Category> categories = restService.getCategories();
        categoriesListener.onCategoriesResponse(categories);
    }

    @Background
    public void getSubCategories(int id) {
        ArrayList<Category> categories = restService.getSubCategories(id);
        categoriesListener.onSubCategoriesResponse(categories);
    }

    @Background
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
        Toast.makeText(mContext, mContext.getString(R.string.item_amount_too_big), Toast.LENGTH_SHORT).show();
    }
}
