package de.handler.mobile.android.fairmondo.data.businessobject.product;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Contains thumbnails for product
 */
public class FairmondoThumbnail implements Parcelable {
    String thumbUrl;
    String originalUrl;

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

    private FairmondoThumbnail(Parcel in) {
        this.thumbUrl = in.readString();
        this.originalUrl = in.readString();
    }

    public FairmondoThumbnail() {
    }

    public static final Creator<FairmondoThumbnail> CREATOR = new Creator<FairmondoThumbnail>() {
        public FairmondoThumbnail createFromParcel(Parcel source) {
            return new FairmondoThumbnail(source);
        }
        public FairmondoThumbnail[] newArray(int size) {
            return new FairmondoThumbnail[size];
        }
    };
}
