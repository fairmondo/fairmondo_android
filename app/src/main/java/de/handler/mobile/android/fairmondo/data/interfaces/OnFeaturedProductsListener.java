package de.handler.mobile.android.fairmondo.data.interfaces;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.network.dto.Product;


/**
 * Listener for featured Products.
 */
public interface OnFeaturedProductsListener {
    void onFeaturesProductsResponse(ArrayList<Product> products);
}
