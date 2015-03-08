package de.handler.mobile.android.fairmondo.datalayer.interfaces;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.networklayer.rest.dto.Product;


/**
 * Listener for featured Products
 */
public interface OnFeaturedProductsListener {
    public void onFeaturesProductsResponse(ArrayList<Product> products);
}
