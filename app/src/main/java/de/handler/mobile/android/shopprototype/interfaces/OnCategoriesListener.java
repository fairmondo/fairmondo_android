package de.handler.mobile.android.shopprototype.interfaces;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.datasource.database.Category;


/**
 * Informs the implementing ui element that the categories response has arrived from server
 */
public interface OnCategoriesListener {
    public void onCategoriesResponse(ArrayList<Category> categories);
    public void onSubCategoriesResponse(ArrayList<Category> categories);
}
