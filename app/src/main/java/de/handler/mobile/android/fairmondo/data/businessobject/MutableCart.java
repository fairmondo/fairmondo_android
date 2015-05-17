package de.handler.mobile.android.fairmondo.data.businessobject;

import org.modelmapper.ModelMapper;

import de.handler.mobile.android.fairmondo.data.businessobject.cart.MutableFairmondoCartItem;

/**
 * Mutable representation of immutable Cart object.
 */
public class MutableCart extends Cart {
    public MutableCart(final Cart immutableCart, final ModelMapper modelMapper) {
        modelMapper.map(immutableCart, this);
    }

    public MutableCart() {
    }

    public void setCartId(final String cartId) {
        this.cartId = cartId;
    }

    public void setCartUrl(final String cartUrl) {
        this.cartUrl = cartUrl;
    }

    public void setCartItem(final MutableFairmondoCartItem cartItem) {
        this.cartItem = cartItem;
    }
}
