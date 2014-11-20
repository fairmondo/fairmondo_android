package de.handler.mobile.android.fairmondo.rest.json.model.article.seller;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Ratings for a seller
 */
public class FairmondoRating implements Parcelable {

    private String url;
    private int count;
    private double positive_percent;
    private double negative_percent;
    private double neutral_percent;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPositive_percent() {
        return positive_percent;
    }

    public void setPositive_percent(double positive_percent) {
        this.positive_percent = positive_percent;
    }

    public double getNegative_percent() {
        return negative_percent;
    }

    public void setNegative_percent(double negative_percent) {
        this.negative_percent = negative_percent;
    }

    public double getNeutral_percent() {
        return neutral_percent;
    }

    public void setNeutral_percent(double neutral_percent) {
        this.neutral_percent = neutral_percent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.count);
        dest.writeDouble(this.positive_percent);
        dest.writeDouble(this.negative_percent);
        dest.writeDouble(this.neutral_percent);
    }

    public FairmondoRating() {
    }

    private FairmondoRating(Parcel in) {
        this.url = in.readString();
        this.count = in.readInt();
        this.positive_percent = in.readDouble();
        this.negative_percent = in.readDouble();
        this.neutral_percent = in.readDouble();
    }

    public static final Parcelable.Creator<FairmondoRating> CREATOR = new Parcelable.Creator<FairmondoRating>() {
        public FairmondoRating createFromParcel(Parcel source) {
            return new FairmondoRating(source);
        }

        public FairmondoRating[] newArray(int size) {
            return new FairmondoRating[size];
        }
    };
}
