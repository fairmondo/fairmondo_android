package de.handler.mobile.android.fairmondo.datalayer.businessobject.product.donation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * subclass of article.donation
 */
public class FairmondoOrganization implements Parcelable {
    private String name;
    private int id;
    private String htmlUrl;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeString(this.htmlUrl);
    }

    private FairmondoOrganization(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.htmlUrl = in.readString();
    }

    public FairmondoOrganization() {
    }

    public static final Creator<FairmondoOrganization> CREATOR = new Creator<FairmondoOrganization>() {
        public FairmondoOrganization createFromParcel(Parcel source) {
            return new FairmondoOrganization(source);
        }
        public FairmondoOrganization[] newArray(int size) {
            return new FairmondoOrganization[size];
        }
    };
}
