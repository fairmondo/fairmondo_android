package de.handler.mobile.android.fairmondo.data.businessobject.cart;

import org.modelmapper.ModelMapper;

/**
 * Mutable representation of immutable FairmondoCartItem.
 */
public class MutableFairmondoCartItem extends FairmondoCartItem {

    public MutableFairmondoCartItem(FairmondoCartItem immutableCartItem, ModelMapper modelMapper) {
        modelMapper.map(immutableCartItem, this);
    }

    public MutableFairmondoCartItem() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLineItemGroupId(int lineItemGroupId) {
        this.lineItemGroupId = lineItemGroupId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}
