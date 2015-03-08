package de.handler.mobile.android.fairmondo.datalayer.businessobject.product.seller;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Ratings for a seller
 */
public class FairmondoRating implements Parcelable {
    private String url;
    private int count;
    private double positivePercent;
    private double negativePercent;
    private double neutralPercent;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.count);
        dest.writeDouble(this.positivePercent);
        dest.writeDouble(this.negativePercent);
        dest.writeDouble(this.neutralPercent);
    }

    public FairmondoRating() {
    }

    private FairmondoRating(Parcel in) {
        this.url = in.readString();
        this.count = in.readInt();
        this.positivePercent = in.readDouble();
        this.negativePercent = in.readDouble();
        this.neutralPercent = in.readDouble();
    }

    public static final Creator<FairmondoRating> CREATOR = new Creator<FairmondoRating>() {
        public FairmondoRating createFromParcel(Parcel source) {
            return new FairmondoRating(source);
        }

        public FairmondoRating[] newArray(int size) {
            return new FairmondoRating[size];
        }
    };
}
