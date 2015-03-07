package de.handler.mobile.android.fairmondo.datalayer.interfaces;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.networklayer.rest.dto.Article;


/**
 * Listener for featured Products
 */
public interface OnFeaturedProductsListener {
    public void onFeaturesProductsResponse(ArrayList<Article> products);
}
