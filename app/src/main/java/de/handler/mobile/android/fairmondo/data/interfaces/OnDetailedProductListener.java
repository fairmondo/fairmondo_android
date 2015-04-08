package de.handler.mobile.android.fairmondo.data.interfaces;

import de.handler.mobile.android.fairmondo.data.businessobject.Product;

/**
 * Informs the implementing ui element of a received product
 */
public interface OnDetailedProductListener {
    public void onDetailedProductResponse(Product product);
}
