package de.handler.mobile.android.fairmondo.interfaces;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.rest.json.Article;


/**
 * Listener for featured Products
 */
public interface OnFeaturedProductsListener {
    public void onFeaturesProductsResponse(ArrayList<Article> products);
}
