package de.handler.mobile.android.fairmondo.data.businessobject;

import org.modelmapper.ModelMapper;

import de.handler.mobile.android.fairmondo.data.businessobject.cart.MutableFairmondoCartItem;

/**
 * Mutable representation of immutable Cart object.
 */
public class MutableCart extends Cart {
    public MutableCart(Cart immutableCart, ModelMapper modelMapper) {
        modelMapper.map(immutableCart, this);
    }

    public MutableCart() {
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public void setCartUrl(String cartUrl) {
        this.cartUrl = cartUrl;
    }

    public void setCartItem(MutableFairmondoCartItem cartItem) {
        this.cartItem = cartItem;
    }
}
