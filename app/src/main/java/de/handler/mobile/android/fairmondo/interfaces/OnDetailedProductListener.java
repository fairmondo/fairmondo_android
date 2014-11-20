package de.handler.mobile.android.fairmondo.interfaces;

import de.handler.mobile.android.fairmondo.rest.json.Article;

/**
 * Informs the implementing ui element of a received product
 */
public interface OnDetailedProductListener {
    public void onDetailedProductResponse(Article article);
}
