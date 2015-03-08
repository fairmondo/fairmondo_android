package de.handler.mobile.android.fairmondo.datalayer.interfaces;

import java.util.List;

import de.handler.mobile.android.fairmondo.datalayer.businessobject.product.FairmondoCategory;


/**
 * Informs the implementing ui element that the categories response has arrived from server
 */
public interface OnCategoriesListener {
    public void onCategoriesResponse(List<FairmondoCategory> categories);
    public void onSubCategoriesResponse(List<FairmondoCategory> categories);
}
