package de.handler.mobile.android.fairmondo.data.businessobject;

import org.modelmapper.ModelMapper;

/**
 * Mutable representation of the immutable FilterRequest.
 */
public class MutableFilterRequest extends FilterRequest {
    public MutableFilterRequest(final FilterRequest immutableFilterRequest, final ModelMapper modelMapper) {
        modelMapper.map(immutableFilterRequest, this);
    }

    public MutableFilterRequest() {
    }

    public void setQ(final String q) {
        this.q = q;
    }

    public void setFair(final boolean fair) {
        this.fair = fair;
    }

    public void setEcologic(final boolean ecologic) {
        this.ecologic = ecologic;
    }

    public void setSmallAndPrecious(final boolean smallAndPrecious) {
        this.smallAndPrecious = smallAndPrecious;
    }

    public void setSwappable(final boolean swappable) {
        this.swappable = swappable;
    }

    public void setBorrowable(final boolean borrowable) {
        this.borrowable = borrowable;
    }

    public void setCondition(final String condition) {
        this.condition = condition;
    }

    public void setCategoryId(final int categoryId) {
        this.categoryId = categoryId;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    public void setSearchInContent(final boolean searchInContent) {
        this.searchInContent = searchInContent;
    }

    public void setPriceFrom(final String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public void setPriceTo(final String priceTo) {
        this.priceTo = priceTo;
    }
}
