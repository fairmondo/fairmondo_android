package de.handler.mobile.android.fairmondo.datalayer.businessobject.product;

import android.os.Parcel;
import android.os.Parcelable;

import de.handler.mobile.android.fairmondo.datalayer.businessobject.product.donation.FairmondoOrganization;

/**
 * Subclass of product
 */
public class FairmondoDonation implements Parcelable {
    private int percent;
    private FairmondoOrganization organization;

    public FairmondoOrganization getOrganization() {
        return organization;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.percent);
        dest.writeParcelable(this.organization, flags);
    }

    private FairmondoDonation(Parcel in) {
        this.percent = in.readInt();
        this.organization = in.readParcelable(FairmondoOrganization.class.getClassLoader());
    }

    public FairmondoDonation() {
    }

    public static final Creator<FairmondoDonation> CREATOR = new Creator<FairmondoDonation>() {
        public FairmondoDonation createFromParcel(Parcel source) {
            return new FairmondoDonation(source);
        }
        public FairmondoDonation[] newArray(int size) {
            return new FairmondoDonation[size];
        }
    };
}
