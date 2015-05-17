package de.handler.mobile.android.fairmondo.data.businessobject;

import org.parceler.Parcel;

/**
 * Incorporates the search request.
 */
@Parcel
public class FilterRequest {
    protected String q;
    protected boolean fair;
    protected boolean ecologic;
    protected boolean smallAndPrecious;
    protected boolean swappable;
    protected boolean borrowable;
    protected String condition;
    protected int categoryId;
    protected String zip;
    protected String orderBy;
    protected boolean searchInContent;
    protected String priceFrom;
    protected String priceTo;

    public String getQ() {
        return q;
    }

    public boolean isFair() {
        return fair;
    }

    public boolean isEcologic() {
        return ecologic;
    }

    public boolean isSmallAndPrecious() {
        return smallAndPrecious;
    }

    public boolean isSwappable() {
        return swappable;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public String getCondition() {
        return condition;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getZip() {
        return zip;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public boolean isSearchInContent() {
        return searchInContent;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }
}
