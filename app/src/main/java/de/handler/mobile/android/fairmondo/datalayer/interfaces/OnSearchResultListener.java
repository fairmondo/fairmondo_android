package de.handler.mobile.android.fairmondo.datalayer.interfaces;


import java.util.List;

import de.handler.mobile.android.fairmondo.datalayer.businessobject.Product;


/**
 * informs the implementing ui element that a search result has arrived
 */
public interface OnSearchResultListener {
    public void onProductsSearchResponse(List<Product> products);
    public void showProgressBar();
    public void hideProgressBar();
}
