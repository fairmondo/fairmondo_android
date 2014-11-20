package de.handler.mobile.android.fairmondo.rest.json.model.article;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Contains thumbnails for product
 */
public class FairmondoThumbnails implements Parcelable {

    String thumb_url;
    String original_url;

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

    public FairmondoThumbnails() {
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

    private FairmondoThumbnails(Parcel in) {
        this.thumb_url = in.readString();
        this.original_url = in.readString();
    }

    public static final Parcelable.Creator<FairmondoThumbnails> CREATOR = new Parcelable.Creator<FairmondoThumbnails>() {
        public FairmondoThumbnails createFromParcel(Parcel source) {
            return new FairmondoThumbnails(source);
        }

        public FairmondoThumbnails[] newArray(int size) {
            return new FairmondoThumbnails[size];
        }
    };
}
