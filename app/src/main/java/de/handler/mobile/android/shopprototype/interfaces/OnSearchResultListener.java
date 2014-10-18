package de.handler.mobile.android.shopprototype.interfaces;


import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.models.Product;

/**
 * informs the implementing ui element that a search result has arrived
 */
public interface OnSearchResultListener {
    public void onProductsSearchResponse(ArrayList<Product> products);
}
