package de.handler.mobile.android.fairmondo.interfaces;


import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.rest.json.Article;


/**
 * informs the implementing ui element that a search result has arrived
 */
public interface OnSearchResultListener {
    public void onProductsSearchResponse(ArrayList<Article> products);
    public void showProgressBar();
}
