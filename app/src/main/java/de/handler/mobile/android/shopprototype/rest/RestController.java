package de.handler.mobile.android.shopprototype.rest;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.rest.json.Article;

/**
 * Encapsulates all communication with the server
 */
@EBean
public class RestController {

    @Bean
    RestServiceErrorHandler errorHandler;

    @RestService
    FairmondoRestService restService;


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
        Articles articles = restService.getProduct(searchString);
        listener.onProductsSearchResponse(articles.getArticles());
    }

    @Background
    public void getProduct(String searchString, int categoryId) {
        Articles articles = restService.getProduct(searchString, categoryId);
        listener.onProductsSearchResponse(articles.getArticles());
    }
}
