package de.handler.mobile.android.fairmondo.data.businessobject;

import org.parceler.Parcel;

import java.util.Arrays;
import java.util.List;

import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoDonation;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoSeller;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoTag;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoThumbnail;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoTitleImage;


/**
 * Business Object representation of the Product object.
 */
@Parcel
public class Product {
    protected String id;
    protected String slug;
    protected String title;
    protected int priceCents = 0;
    protected int quantityAvailable = 0;
    protected int vat = 0;
    protected FairmondoTag tags;
    protected FairmondoDonation donation;
    protected FairmondoCategory[] categories;
    protected FairmondoTitleImage titleImage;
    protected String titleImageUrl;
    protected FairmondoThumbnail[] thumbnails;
    protected FairmondoSeller seller;
    protected String content;
    protected String paymentHtml;
    protected String transportHtml;
    protected String commendationHtml;
    protected String htmlUrl;

    public String getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPriceCents() {
        return priceCents;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public Integer getVat() {
        return vat;
    }

    public FairmondoTag getTags() {
        return tags;
    }

    public FairmondoDonation getDonation() {
        return donation;
    }

    public List<FairmondoCategory> getCategories() {
        return Arrays.asList(categories);
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public FairmondoTitleImage getTitleImage() {
        return titleImage;
    }

    public List<FairmondoThumbnail> getThumbnails() {
        return Arrays.asList(thumbnails);
    }

    public FairmondoSeller getSeller() {
        return seller;
    }

    public String getContent() {
        return content;
    }

    public String getPaymentHtml() {
        return paymentHtml;
    }

    public String getTransportHtml() {
        return transportHtml;
    }

    public String getCommendationHtml() {
        return commendationHtml;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }
}
