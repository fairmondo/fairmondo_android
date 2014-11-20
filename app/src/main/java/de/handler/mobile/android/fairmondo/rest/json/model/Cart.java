package de.handler.mobile.android.fairmondo.rest.json.model;

import org.androidannotations.annotations.EBean;

import de.handler.mobile.android.fairmondo.rest.json.model.cart.FairmondoCartItem;

/**
 * Cart containing all the products a customer wants to buy
 */
@EBean(scope = EBean.Scope.Singleton)
public class Cart {

    private int cart_id;
    private String cart_url;
    private FairmondoCartItem line_item;


    public int getCart_id() {
        return cart_id;
    }

    public String getCart_url() {
        return cart_url;
    }

    public FairmondoCartItem getLine_item() {
        return line_item;
    }
}
