package de.handler.mobile.android.fairmondo.data.interfaces;

import de.handler.mobile.android.fairmondo.data.businessobject.Cart;

/**
 * Notifies implementing class that a product has been removed or added from cart
 */
public interface OnCartChangeListener {
    public void onCartChanged(Cart cart);
}
