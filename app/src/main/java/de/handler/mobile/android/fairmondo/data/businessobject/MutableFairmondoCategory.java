package de.handler.mobile.android.fairmondo.data.businessobject;

import android.os.Parcel;
import android.os.Parcelable;

import org.modelmapper.ModelMapper;

import java.util.List;

import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;

/**
 * Mutable instance of immutable FairmondoCategory.
 */
public class MutableFairmondoCategory extends FairmondoCategory {
    public MutableFairmondoCategory(FairmondoCategory immutableFairmondoCategory, ModelMapper modelMapper) {
        modelMapper.map(this, immutableFairmondoCategory);
    }

    public MutableFairmondoCategory() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAncestors(List<String> ancestors) {
        this.ancestors = ancestors;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.slug);
        dest.writeString(this.name);
        dest.writeStringList(this.ancestors);
    }

    private MutableFairmondoCategory(Parcel in) {
        this.id = in.readString();
        this.slug = in.readString();
        this.name = in.readString();
        in.readStringList(this.ancestors);
    }

    public static final Parcelable.Creator<FairmondoCategory> CREATOR = new Parcelable.Creator<FairmondoCategory>() {
        public FairmondoCategory createFromParcel(Parcel source) {
            return new MutableFairmondoCategory(source);
        }

        public FairmondoCategory[] newArray(int size) {
            return new MutableFairmondoCategory[size];
        }
    };
}
