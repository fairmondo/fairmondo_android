package de.handler.mobile.android.fairmondo.data.businessobject.product;

import org.parceler.Parcel;

import de.handler.mobile.android.fairmondo.data.businessobject.product.donation.FairmondoOrganization;

/**
 * Subclass of product.
 */
@Parcel
public class FairmondoDonation {
    protected Integer percent;
    protected FairmondoOrganization organization;

    public FairmondoOrganization getOrganization() {
        return organization;
    }

    public Integer getPercent() {
        return percent;
    }
}
