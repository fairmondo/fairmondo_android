package de.handler.mobile.android.shopprototype.interfaces;

import de.handler.mobile.android.shopprototype.rest.json.Article;

/**
 * Informs the implementing ui element of a received product
 */
public interface OnDetailedProductListener {
    public void onDetailedProductResponse(Article article);
}
