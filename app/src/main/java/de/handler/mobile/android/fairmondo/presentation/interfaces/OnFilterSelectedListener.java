package de.handler.mobile.android.fairmondo.presentation.interfaces;

/**
 * Informs the implementing Activity or Fragment of filter selections.
 */
public interface OnFilterSelectedListener {
    /**
     * Callback for the filter "fair".
     */
    void onFairFilterSelected(boolean condition);

    /**
     * Callback for the filter "ecological".
     */
    void onEcologicalFilterSelected(boolean condition);
}
