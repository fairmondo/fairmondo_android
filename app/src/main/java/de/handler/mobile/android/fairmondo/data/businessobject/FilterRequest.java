package de.handler.mobile.android.fairmondo.data.businessobject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Incorporates the search request
 */
public class FilterRequest implements Parcelable {
    protected String q;
    protected boolean fair;
    protected boolean ecologic;
    protected boolean smallAndPrecious;
    protected boolean swappable;
    protected boolean borrowable;
    protected String condition;
    protected int categoryId;
    protected String zip;
    protected String orderBy;
    protected boolean searchInContent;
    protected String priceFrom;
    protected String priceTo;

    public String getQ() {
        return q;
    }

    public boolean isFair() {
        return fair;
    }

    public boolean isEcologic() {
        return ecologic;
    }

    public boolean isSmallAndPrecious() {
        return smallAndPrecious;
    }

    public boolean isSwappable() {
        return swappable;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public String getCondition() {
        return condition;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getZip() {
        return zip;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public boolean isSearchInContent() {
        return searchInContent;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.q);
        dest.writeByte(fair ? (byte) 1 : (byte) 0);
        dest.writeByte(ecologic ? (byte) 1 : (byte) 0);
        dest.writeByte(smallAndPrecious ? (byte) 1 : (byte) 0);
        dest.writeByte(swappable ? (byte) 1 : (byte) 0);
        dest.writeByte(borrowable ? (byte) 1 : (byte) 0);
        dest.writeString(this.condition);
        dest.writeInt(this.categoryId);
        dest.writeString(this.zip);
        dest.writeString(this.orderBy);
        dest.writeByte(searchInContent ? (byte) 1 : (byte) 0);
        dest.writeString(this.priceFrom);
        dest.writeString(this.priceTo);
    }

    public FilterRequest() {
    }

    protected FilterRequest(Parcel in) {
        this.q = in.readString();
        this.fair = in.readByte() != 0;
        this.ecologic = in.readByte() != 0;
        this.smallAndPrecious = in.readByte() != 0;
        this.swappable = in.readByte() != 0;
        this.borrowable = in.readByte() != 0;
        this.condition = in.readString();
        this.categoryId = in.readInt();
        this.zip = in.readString();
        this.orderBy = in.readString();
        this.searchInContent = in.readByte() != 0;
        this.priceFrom = in.readString();
        this.priceTo = in.readString();
    }

    public static final Creator<FilterRequest> CREATOR = new Creator<FilterRequest>() {
        public FilterRequest createFromParcel(Parcel source) {
            return new FilterRequest(source);
        }
        public FilterRequest[] newArray(int size) {
            return new FilterRequest[size];
        }
    };
}
