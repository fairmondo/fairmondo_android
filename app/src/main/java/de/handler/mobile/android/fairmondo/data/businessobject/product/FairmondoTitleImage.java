package de.handler.mobile.android.fairmondo.data.businessobject.product;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Image URLs for one product
 */
public class FairmondoTitleImage implements Parcelable {
    private String thumbUrl;
    private String originalUrl;

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbUrl);
        dest.writeString(this.originalUrl);
    }

    private FairmondoTitleImage(Parcel in) {
        this.thumbUrl = in.readString();
        this.originalUrl = in.readString();
    }

    public FairmondoTitleImage() {
    }

    public static final Creator<FairmondoTitleImage> CREATOR = new Creator<FairmondoTitleImage>() {
        public FairmondoTitleImage createFromParcel(Parcel source) {
            return new FairmondoTitleImage(source);
        }
        public FairmondoTitleImage[] newArray(int size) {
            return new FairmondoTitleImage[size];
        }
    };
}
