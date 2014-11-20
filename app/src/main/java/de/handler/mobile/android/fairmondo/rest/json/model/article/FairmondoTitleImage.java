package de.handler.mobile.android.fairmondo.rest.json.model.article;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Image URLs for one product
 */
public class FairmondoTitleImage implements Parcelable {

    private String thumb_url;
    private String original_url;


    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumb_url);
        dest.writeString(this.original_url);
    }

    public FairmondoTitleImage() {
    }

    private FairmondoTitleImage(Parcel in) {
        this.thumb_url = in.readString();
        this.original_url = in.readString();
    }

    public static final Parcelable.Creator<FairmondoTitleImage> CREATOR = new Parcelable.Creator<FairmondoTitleImage>() {
        public FairmondoTitleImage createFromParcel(Parcel source) {
            return new FairmondoTitleImage(source);
        }

        public FairmondoTitleImage[] newArray(int size) {
            return new FairmondoTitleImage[size];
        }
    };
}
