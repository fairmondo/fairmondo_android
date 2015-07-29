package de.handler.mobile.android.fairmondo.presentation.interfaces;

import de.handler.mobile.android.fairmondo.presentation.fragments.SortFragment;

/**
 * React to filter selections in the {@link SortFragment}.
 */
public interface OnSortingSelectedListener {
    /**
     * Informs the implementing activity that the price filter has been selected.
     * @param selection the state of the switch
     */
    void onPriceSortSelected(boolean selection);

    /**
     * Informs the implementing activity that the price filter has been selected.
     * @param selection the state of the switch
     */
    void onAlphabeticalSortSelected(boolean selection);

    /**
     * Informs the implementing activity that the condition filter has been selected.
     * @param selection the state of the switch
     */
    void onConditionSortSelected(boolean selection);
}
