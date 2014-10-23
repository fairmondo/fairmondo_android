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

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;

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


    private OnSearchResultListener listener;
    private Context mContext;


    public RestController(Context context) {
        mContext = context;
    }

    @AfterInject
    public void initRestService() {
        errorHandler.setContext(mContext);
        restService.setRestErrorHandler(errorHandler);
    }

    public void setListener(OnSearchResultListener listener) {
        this.listener = listener;
    }

    @Background
    public void getProduct(String searchString) {
        if (app.isConnected()) {

            Articles articles = restService.getProduct(searchString);
            if (articles != null && articles.getArticles() != null) {
                listener.onProductsSearchResponse(articles.getArticles());
            } else {
                listener.onProductsSearchResponse(null);
            }
        } else {
            makeToast();
        }
    }


    @Background
    public void getProduct(String searchString, int categoryId) {
        if (app.isConnected()) {
            Articles articles = restService.getProduct(searchString, categoryId);
            if (articles != null && articles.getArticles() != null) {
                listener.onProductsSearchResponse(articles.getArticles());
            } else {
                listener.onProductsSearchResponse(null);
            }
        } else {
            makeToast();
        }
    }


    @UiThread
    public void makeToast() {
        Toast.makeText(mContext, mContext.getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
    }
}
