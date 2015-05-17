package de.handler.mobile.android.fairmondo.network.dto;

import de.handler.mobile.android.fairmondo.network.dto.cart.FairmondoCartItem;

/**
 * Cart containing all the products a customer wants to buy
 */
public class Cart {
    public int cart_id;
    public String cart_url;
    public FairmondoCartItem line_item;
}
