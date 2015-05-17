package de.handler.mobile.android.fairmondo.data.businessobject.product;

import org.parceler.Parcel;

/**
 * Image URLs for one product.
 */
@Parcel
public class FairmondoTitleImage {
    protected String thumbUrl;
    protected String originalUrl;

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
