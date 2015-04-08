package de.handler.mobile.android.fairmondo.network.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Incorporates the search request
 */
public class FilterRequest implements Parcelable {

    /**

     enumerize :condition, in: [:new, :old]
     enumerize :order_by, in: [:newest,:cheapest,:most_expensive,:old,:new,:fair,:ecologic,:small_and_precious,:most_donated]
     # => :newest,"Preis aufsteigend" => :cheapest,"Preis absteigend" => :most_expensive
    */

    private String q;
    private boolean fair;
    private boolean ecologic;
    private boolean small_and_precious;
    private boolean swappable;
    private boolean borrowable;
    private String condition;
    private int category_id;
    private String zip;
    private String order_by;
    private boolean search_in_content;
    private String price_from;
    private String price_to;

    public FilterRequest(String q, boolean fair, boolean ecologic, boolean small_and_precious, boolean swappable, boolean borrowable, String condition, int category_id, String zip, String order_by, boolean search_in_content, String price_from, String price_to) {
        this.q = q;
        this.fair = fair;
        this.ecologic = ecologic;
        this.small_and_precious = small_and_precious;
        this.swappable = swappable;
        this.borrowable = borrowable;
        this.condition = condition;
        this.category_id = category_id;
        this.zip = zip;
        this.order_by = order_by;
        this.search_in_content = search_in_content;
        this.price_from = price_from;
        this.price_to = price_to;
    }

    public FilterRequest() {}

    public void setQ(String q) {
        this.q = q;
    }

    public void setFair(boolean fair) {
        this.fair = fair;
    }

    public void setEcologic(boolean ecologic) {
        this.ecologic = ecologic;
    }

    public void setSmall_and_precious(boolean small_and_precious) {
        this.small_and_precious = small_and_precious;
    }

    public void setSwappable(boolean swappable) {
        this.swappable = swappable;
    }

    public void setBorrowable(boolean borrowable) {
        this.borrowable = borrowable;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public void setSearch_in_content(boolean search_in_content) {
        this.search_in_content = search_in_content;
    }

    public void setPrice_from(String price_from) {
        this.price_from = price_from;
    }

    public void setPrice_to(String price_to) {
        this.price_to = price_to;
    }

    public String getQ() {

        return q;
    }

    public boolean isFair() {
        return fair;
    }

    public boolean isEcologic() {
        return ecologic;
    }

    public boolean isSmall_and_precious() {
        return small_and_precious;
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

    public int getCategory_id() {
        return category_id;
    }

    public String getZip() {
        return zip;
    }

    public String getOrder_by() {
        return order_by;
    }

    public boolean isSearch_in_content() {
        return search_in_content;
    }

    public String getPrice_from() {
        return price_from;
    }

    public String getPrice_to() {
        return price_to;
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
        dest.writeByte(small_and_precious ? (byte) 1 : (byte) 0);
        dest.writeByte(swappable ? (byte) 1 : (byte) 0);
        dest.writeByte(borrowable ? (byte) 1 : (byte) 0);
        dest.writeString(this.condition);
        dest.writeInt(this.category_id);
        dest.writeString(this.zip);
        dest.writeString(this.order_by);
        dest.writeByte(search_in_content ? (byte) 1 : (byte) 0);
        dest.writeString(this.price_from);
        dest.writeString(this.price_to);
    }

    private FilterRequest(Parcel in) {
        this.q = in.readString();
        this.fair = in.readByte() != 0;
        this.ecologic = in.readByte() != 0;
        this.small_and_precious = in.readByte() != 0;
        this.swappable = in.readByte() != 0;
        this.borrowable = in.readByte() != 0;
        this.condition = in.readString();
        this.category_id = in.readInt();
        this.zip = in.readString();
        this.order_by = in.readString();
        this.search_in_content = in.readByte() != 0;
        this.price_from = in.readString();
        this.price_to = in.readString();
    }

    public static final Parcelable.Creator<FilterRequest> CREATOR = new Parcelable.Creator<FilterRequest>() {
        public FilterRequest createFromParcel(Parcel source) {
            return new FilterRequest(source);
        }

        public FilterRequest[] newArray(int size) {
            return new FilterRequest[size];
        }
    };
}
