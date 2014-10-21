package de.handler.mobile.android.shopprototype.interfaces;


import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.rest.json.Article;


/**
 * informs the implementing ui element that a search result has arrived
 */
public interface OnSearchResultListener {
    public void onProductsSearchResponse(ArrayList<Article> products);
}
