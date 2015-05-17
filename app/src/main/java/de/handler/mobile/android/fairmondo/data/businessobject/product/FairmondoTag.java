package de.handler.mobile.android.fairmondo.data.businessobject.product;

import org.parceler.Parcel;

/**
 * Subclass of article.
 */
@Parcel
public class FairmondoTag {
    protected String condition;
    protected Boolean fair;
    protected Boolean ecologic;
    protected Boolean smallAndPrecious;
    protected Boolean borrowable;
    protected Boolean swappable;

    public String getCondition() {
        return condition;
    }

    public Boolean getFair() {
        return fair;
    }

    public Boolean getEcologic() {
        return ecologic;
    }

    public Boolean getSmallAndPrecious() {
        return smallAndPrecious;
    }

    public Boolean getBorrowable() {
        return borrowable;
    }

    public Boolean getSwappable() {
        return swappable;
    }
}
