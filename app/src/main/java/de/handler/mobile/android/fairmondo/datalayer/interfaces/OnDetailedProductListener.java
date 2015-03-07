package de.handler.mobile.android.fairmondo.datalayer.interfaces;

import de.handler.mobile.android.fairmondo.networklayer.rest.dto.Article;

/**
 * Informs the implementing ui element of a received product
 */
public interface OnDetailedProductListener {
    public void onDetailedProductResponse(Article article);
}
