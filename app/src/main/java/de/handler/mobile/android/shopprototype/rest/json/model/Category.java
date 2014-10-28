package de.handler.mobile.android.shopprototype.rest.json.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The category for a product
 */
public class Category implements Parcelable {
    private String name;
    private int id;
    private String slug;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeString(this.slug);
    }

    public Category() {
    }

    private Category(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.slug = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
