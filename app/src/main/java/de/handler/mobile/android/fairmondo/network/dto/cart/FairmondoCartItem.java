package de.handler.mobile.android.fairmondo.network.dto.cart;

/**
 * An item representing a product in the cart.
 */
public class FairmondoCartItem {
    public int id;
    public int line_item_group_id;
    public int article_id;
    public int requested_quantity;
    public String created_at;
    public String updated_at;
}
