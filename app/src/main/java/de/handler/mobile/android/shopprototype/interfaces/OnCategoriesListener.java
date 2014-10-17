package de.handler.mobile.android.shopprototype.interfaces;

import java.util.ArrayList;

/**
 * Informs the implementing ui element that the categories response has arrived from server
 */
public interface OnCategoriesListener {
    public void onCategoriesResponse(ArrayList<String> categories);
}
