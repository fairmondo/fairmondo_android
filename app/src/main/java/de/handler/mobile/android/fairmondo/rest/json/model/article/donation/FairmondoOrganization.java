package de.handler.mobile.android.fairmondo.rest.json.model.article.donation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * subclass of article.donation
 */
public class FairmondoOrganization implements Parcelable {
    private String name;
    private int id;
    private String html_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeString(this.html_url);
    }

    public FairmondoOrganization() {
    }

    private FairmondoOrganization(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.html_url = in.readString();
    }

    public static final Parcelable.Creator<FairmondoOrganization> CREATOR = new Parcelable.Creator<FairmondoOrganization>() {
        public FairmondoOrganization createFromParcel(Parcel source) {
            return new FairmondoOrganization(source);
        }

        public FairmondoOrganization[] newArray(int size) {
            return new FairmondoOrganization[size];
        }
    };
}
