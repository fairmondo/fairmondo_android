package de.handler.mobile.android.fairmondo.data.businessobject.cart;

import org.modelmapper.ModelMapper;

/**
 * Mutable representation of immutable FairmondoCartItem.
 */
public class MutableFairmondoCartItem extends FairmondoCartItem {
    public MutableFairmondoCartItem(final FairmondoCartItem immutableCartItem, final ModelMapper modelMapper) {
        modelMapper.map(immutableCartItem, this);
    }

    public MutableFairmondoCartItem() {
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setLineItemGroupId(final int lineItemGroupId) {
        this.lineItemGroupId = lineItemGroupId;
    }

    public void setArticleId(final int articleId) {
        this.articleId = articleId;
    }

    public void setRequestedQuantity(final int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}
