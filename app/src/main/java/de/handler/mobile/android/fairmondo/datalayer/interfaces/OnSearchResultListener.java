package de.handler.mobile.android.fairmondo.datalayer.interfaces;


import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.networklayer.rest.dto.Article;


/**
 * informs the implementing ui element that a search result has arrived
 */
public interface OnSearchResultListener {
    public void onProductsSearchResponse(ArrayList<Article> products);
    public void showProgressBar();
    public void hideProgressBar();
}
