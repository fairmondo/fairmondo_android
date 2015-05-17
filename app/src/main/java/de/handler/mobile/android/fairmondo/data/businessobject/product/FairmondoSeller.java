package de.handler.mobile.android.fairmondo.data.businessobject.product;

import org.parceler.Parcel;

import de.handler.mobile.android.fairmondo.data.businessobject.product.seller.FairmondoRating;

/**
 * Subclass of article.
 */
@Parcel
public class FairmondoSeller {
    protected String nickname;
    protected boolean legalEntity;
    protected boolean ngo;
    protected boolean vacationing;
    protected String name;
    protected String type;
    protected String typeName;
    protected String htmlUrl;
    protected String imageUrl;
    protected FairmondoRating ratings;
    protected String terms;

    public String getNickname() {
        return nickname;
    }

    public Boolean getLegalEntity() {
        return legalEntity;
    }

    public Boolean getNgo() {
        return ngo;
    }

    public Boolean getVacationing() {
        return vacationing;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FairmondoRating getRatings() {
        return ratings;
    }

    public String getTerms() {
        return terms;
    }
}
