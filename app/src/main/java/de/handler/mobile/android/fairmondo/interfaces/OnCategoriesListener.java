package de.handler.mobile.android.fairmondo.interfaces;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.datasource.database.Category;


/**
 * Informs the implementing ui element that the categories response has arrived from server
 */
public interface OnCategoriesListener {
    public void onCategoriesResponse(ArrayList<Category> categories);
    public void onSubCategoriesResponse(ArrayList<Category> categories);
}
