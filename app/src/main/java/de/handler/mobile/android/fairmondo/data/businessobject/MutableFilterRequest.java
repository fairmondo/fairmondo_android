package de.handler.mobile.android.fairmondo.data.businessobject;

import org.modelmapper.ModelMapper;

/**
 * Mutable representation of the immutable FilterRequest
 */
public class MutableFilterRequest extends FilterRequest {
    public MutableFilterRequest(FilterRequest immutableFilterRequest, ModelMapper modelMapper) {
        modelMapper.map(immutableFilterRequest, this);
    }

    public MutableFilterRequest() {
    }



    public void setQ(String q) {
        this.q = q;
    }

    public void setFair(boolean fair) {
        this.fair = fair;
    }

    public void setEcologic(boolean ecologic) {
        this.ecologic = ecologic;
    }

    public void setSmallAndPrecious(boolean smallAndPrecious) {
        this.smallAndPrecious = smallAndPrecious;
    }

    public void setSwappable(boolean swappable) {
        this.swappable = swappable;
    }

    public void setBorrowable(boolean borrowable) {
        this.borrowable = borrowable;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setSearchInContent(boolean searchInContent) {
        this.searchInContent = searchInContent;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public void setPriceTo(String priceTo) {
        this.priceTo = priceTo;
    }
}
