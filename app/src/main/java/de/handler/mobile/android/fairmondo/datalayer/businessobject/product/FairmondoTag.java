package de.handler.mobile.android.fairmondo.datalayer.businessobject.product;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Subclass of article
 */
public class FairmondoTag implements Parcelable {
    private String condition;
    private Boolean fair;
    private Boolean ecologic;
    private Boolean smallAndPrecious;
    private Boolean borrowable;
    private Boolean swappable;

    public String getCondition() {
        return condition;
    }

    public Boolean getFair() {
        return fair;
    }

    public Boolean getEcologic() {
        return ecologic;
    }

    public Boolean getSmallAndPrecious() {
        return smallAndPrecious;
    }

    public Boolean getBorrowable() {
        return borrowable;
    }

    public Boolean getSwappable() {
        return swappable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.condition);
        dest.writeValue(this.fair);
        dest.writeValue(this.ecologic);
        dest.writeValue(this.smallAndPrecious);
        dest.writeValue(this.borrowable);
        dest.writeValue(this.swappable);
    }

    private FairmondoTag(Parcel in) {
        this.condition = in.readString();
        this.fair = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ecologic = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.smallAndPrecious = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.borrowable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.swappable = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public FairmondoTag() {
    }

    public static final Creator<FairmondoTag> CREATOR = new Creator<FairmondoTag>() {
        public FairmondoTag createFromParcel(Parcel source) {
            return new FairmondoTag(source);
        }
        public FairmondoTag[] newArray(int size) {
            return new FairmondoTag[size];
        }
    };
}
