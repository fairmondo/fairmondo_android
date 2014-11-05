package de.handler.mobile.android.shopprototype.rest.json.model.article;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Categories returned with a product
 */
public class FairmondoCategories implements Parcelable {

    private String name;
    private ArrayList<String> ancestors;


    public ArrayList<String> getAncestors() {
        return ancestors;
    }

    public void setAncestors(ArrayList<String> ancestors) {
        this.ancestors = ancestors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FairmondoCategories() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeSerializable(this.ancestors);
    }

    private FairmondoCategories(Parcel in) {
        this.name = in.readString();
        this.ancestors = (ArrayList<String>) in.readSerializable();
    }

    public static final Parcelable.Creator<FairmondoCategories> CREATOR = new Parcelable.Creator<FairmondoCategories>() {
        public FairmondoCategories createFromParcel(Parcel source) {
            return new FairmondoCategories(source);
        }

        public FairmondoCategories[] newArray(int size) {
            return new FairmondoCategories[size];
        }
    };
}
