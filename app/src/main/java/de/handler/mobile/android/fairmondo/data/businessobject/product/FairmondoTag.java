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

    public Boolean isFair() {
        return fair;
    }

    public Boolean isEcologic() {
        return ecologic;
    }

    public Boolean isSmallAndPrecious() {
        return smallAndPrecious;
    }

    public Boolean isBorrowable() {
        return borrowable;
    }

    public Boolean isSwappable() {
        return swappable;
    }
}
