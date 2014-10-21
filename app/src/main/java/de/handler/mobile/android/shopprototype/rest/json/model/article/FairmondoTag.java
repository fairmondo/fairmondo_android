package de.handler.mobile.android.shopprototype.rest.json.model.article;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Subclass of article
 */
public class FairmondoTag implements Parcelable {
    private String condition;
    private Boolean fair;
    private Boolean ecologic;
    private Boolean small_and_precious;
    private Boolean borrowable;
    private Boolean swappable;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getFair() {
        return fair;
    }

    public void setFair(Boolean fair) {
        this.fair = fair;
    }

    public Boolean getEcologic() {
        return ecologic;
    }

    public void setEcologic(Boolean ecologic) {
        this.ecologic = ecologic;
    }

    public Boolean getSmall_and_precious() {
        return small_and_precious;
    }

    public void setSmall_and_precious(Boolean small_and_precious) {
        this.small_and_precious = small_and_precious;
    }

    public Boolean getBorrowable() {
        return borrowable;
    }

    public void setBorrowable(Boolean borrowable) {
        this.borrowable = borrowable;
    }

    public Boolean getSwappable() {
        return swappable;
    }

    public void setSwappable(Boolean swappable) {
        this.swappable = swappable;
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
        dest.writeValue(this.small_and_precious);
        dest.writeValue(this.borrowable);
        dest.writeValue(this.swappable);
    }

    public FairmondoTag() {
    }

    private FairmondoTag(Parcel in) {
        this.condition = in.readString();
        this.fair = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ecologic = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.small_and_precious = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.borrowable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.swappable = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<FairmondoTag> CREATOR = new Parcelable.Creator<FairmondoTag>() {
        public FairmondoTag createFromParcel(Parcel source) {
            return new FairmondoTag(source);
        }

        public FairmondoTag[] newArray(int size) {
            return new FairmondoTag[size];
        }
    };
}
