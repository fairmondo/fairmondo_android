package de.handler.mobile.android.fairmondo.data.businessobject.product.seller;

import org.parceler.Parcel;

/**
 * Ratings for a seller.
 */
@Parcel
public class FairmondoRating {
    protected String url;
    protected Integer count;
    protected Double positivePercent;
    protected Double negativePercent;
    protected Double neutralPercent;

    public String getUrl() {
        return url;
    }

    public int getCount() {
        return count;
    }

    public double getPositivePercent() {
        return positivePercent;
    }

    public double getNegativePercent() {
        return negativePercent;
    }

    public double getNeutralPercent() {
        return neutralPercent;
    }
}
