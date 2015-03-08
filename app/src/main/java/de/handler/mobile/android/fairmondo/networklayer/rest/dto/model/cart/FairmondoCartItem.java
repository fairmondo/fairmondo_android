package de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.cart;

/**
 * An item representing a product in the cart
 */
public class FairmondoCartItem {

    private int id;
    private int line_item_group_id;
    private int article_id;
    private int requested_quantity;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public int getLine_item_group_id() {
        return line_item_group_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public int getRequested_quantity() {
        return requested_quantity;
    }
}
