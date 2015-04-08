package de.handler.mobile.android.fairmondo.data.businessobject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoDonation;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoSeller;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoTag;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoThumbnail;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoTitleImage;


/**
 * Model for Rest Communication
 * Represents the structure returned
 * as json by Fairmondo server
 */
public class Product implements Parcelable {
    public Long id;
    public String slug;
    public String title;
    public int priceCents = 0;
    public int quantityAvailable = 0;
    public int vat = 0;
    public FairmondoTag tags;
    public FairmondoDonation donation;
    public FairmondoCategory[] categories;
    public FairmondoTitleImage titleImage;
    public String titleImageUrl;
    public FairmondoThumbnail[] thumbnails;
    public FairmondoSeller seller;
    public String content;
    public String paymentHtml;
    public String transportHtml;
    public String commendationHtml;
    public String htmlUrl;

    public Long getId() {
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

    public Product() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.slug);
        dest.writeString(this.title);
        dest.writeInt(this.priceCents);
        dest.writeInt(this.quantityAvailable);
        dest.writeInt(this.vat);
        dest.writeParcelable(this.tags, 0);
        dest.writeParcelable(this.donation, 0);
        dest.writeTypedArray(this.categories, flags);
        dest.writeParcelable(this.titleImage, 0);
        dest.writeString(this.titleImageUrl);
        dest.writeTypedArray(this.thumbnails, flags);
        dest.writeParcelable(this.seller, 0);
        dest.writeString(this.content);
        dest.writeString(this.paymentHtml);
        dest.writeString(this.transportHtml);
        dest.writeString(this.commendationHtml);
        dest.writeString(this.htmlUrl);
    }

    private Product(Parcel in) {
        this.id = in.readLong();
        this.slug = in.readString();
        this.title = in.readString();
        this.priceCents = in.readInt();
        this.quantityAvailable = in.readInt();
        this.vat = in.readInt();
        this.tags = in.readParcelable(FairmondoTag.class.getClassLoader());
        this.donation = in.readParcelable(FairmondoDonation.class.getClassLoader());
        in.createTypedArray(FairmondoCategory.CREATOR);
        this.titleImage = in.readParcelable(FairmondoTitleImage.class.getClassLoader());
        this.titleImageUrl = in.readString();
        in.createTypedArray(FairmondoThumbnail.CREATOR);
        this.seller = in.readParcelable(FairmondoSeller.class.getClassLoader());
        this.content = in.readString();
        this.paymentHtml = in.readString();
        this.transportHtml = in.readString();
        this.commendationHtml = in.readString();
        this.htmlUrl = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
