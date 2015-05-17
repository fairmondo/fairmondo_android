package de.handler.mobile.android.fairmondo.data.businessobject.product.donation;

import org.parceler.Parcel;

/**
 * subclass of article.donation.
 */
@Parcel
public class FairmondoOrganization {
    protected String name;
    protected int id;
    protected String htmlUrl;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }
}
