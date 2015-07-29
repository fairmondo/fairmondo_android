package de.handler.mobile.android.fairmondo.presentation.interfaces;

import android.view.View;

/**
 * A item click listener which informs implementing class of a click event in an adapter.
 */
public interface OnRecyclerItemClickListener<T> {
    void onRecyclerItemClicked(View view, T item);
}
