package de.handler.mobile.android.fairmondo.data.businessobject.cart;

/**
 * An item representing a product in the cart
 */
public class FairmondoCartItem {
    protected String id;
    protected int lineItemGroupId;
    protected int articleId;
    protected int requestedQuantity;

    public String getId() {
        return id;
    }

    public int getLineItemGroupId() {
        return lineItemGroupId;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }
}
