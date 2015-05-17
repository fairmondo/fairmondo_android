package de.handler.mobile.android.fairmondo.data.businessobject.product;

import org.parceler.Parcel;

/**
 * Contains thumbnails for product.
 */
@Parcel
public class FairmondoThumbnail {
    protected String thumbUrl;
    protected String originalUrl;

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
