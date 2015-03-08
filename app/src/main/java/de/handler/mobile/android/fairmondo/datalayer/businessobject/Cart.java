package de.handler.mobile.android.fairmondo.datalayer.businessobject;

import org.androidannotations.annotations.EBean;

import de.handler.mobile.android.fairmondo.datalayer.businessobject.cart.FairmondoCartItem;

/**
 * Cart containing all the products a customer wants to buy
 */
@EBean(scope = EBean.Scope.Singleton)
public class Cart {
    protected String cartId;
    protected String cartUrl;
    protected FairmondoCartItem cartItem;

    public String getCartId() {
        return cartId;
    }

    public String getCartUrl() {
        return cartUrl;
    }

    public FairmondoCartItem getCartItem() {
        return cartItem;
    }
}
