package de.handler.mobile.android.fairmondo.data.businessobject.product;

import android.os.Parcel;
import android.os.Parcelable;

import de.handler.mobile.android.fairmondo.data.businessobject.product.seller.FairmondoRating;

/**
 * Subclass of article
 */
public class FairmondoSeller implements Parcelable {
    private String nickname;
    private boolean legalEntity;
    private boolean ngo;
    private boolean vacationing;
    private String name;
    private String type;
    private String typeName;
    private String htmlUrl;
    private String imageUrl;
    private FairmondoRating ratings;
    private String terms;

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

    public FairmondoSeller() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickname);
        dest.writeByte(legalEntity ? (byte) 1 : (byte) 0);
        dest.writeByte(ngo ? (byte) 1 : (byte) 0);
        dest.writeByte(vacationing ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.typeName);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.imageUrl);
        dest.writeParcelable(this.ratings, 0);
        dest.writeString(this.terms);
    }

    private FairmondoSeller(Parcel in) {
        this.nickname = in.readString();
        this.legalEntity = in.readByte() != 0;
        this.ngo = in.readByte() != 0;
        this.vacationing = in.readByte() != 0;
        this.name = in.readString();
        this.type = in.readString();
        this.typeName = in.readString();
        this.htmlUrl = in.readString();
        this.imageUrl = in.readString();
        this.ratings = in.readParcelable(FairmondoRating.class.getClassLoader());
        this.terms = in.readString();
    }

    public static final Creator<FairmondoSeller> CREATOR = new Creator<FairmondoSeller>() {
        public FairmondoSeller createFromParcel(Parcel source) {
            return new FairmondoSeller(source);
        }

        public FairmondoSeller[] newArray(int size) {
            return new FairmondoSeller[size];
        }
    };
}
