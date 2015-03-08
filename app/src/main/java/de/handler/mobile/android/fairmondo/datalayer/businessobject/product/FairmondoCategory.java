package de.handler.mobile.android.fairmondo.datalayer.businessobject.product;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Categories returned with a product
 */
public class FairmondoCategory implements Parcelable {
    protected Long id = -1L;
    protected String name;
    protected String slug;
    protected List<String> ancestors;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public List<String> getAncestors() {
        return ancestors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.slug);
        dest.writeString(this.name);
        dest.writeStringList(this.ancestors);
    }

    public FairmondoCategory() {
    }

    private FairmondoCategory(Parcel in) {
        this.id = in.readLong();
        this.slug = in.readString();
        this.name = in.readString();
        in.readStringList(this.ancestors);
    }

    public static final Parcelable.Creator<FairmondoCategory> CREATOR = new Parcelable.Creator<FairmondoCategory>() {
        public FairmondoCategory createFromParcel(Parcel source) {
            return new FairmondoCategory(source);
        }

        public FairmondoCategory[] newArray(int size) {
            return new FairmondoCategory[size];
        }
    };
}
