package de.handler.mobile.android.fairmondo.rest.json.model.article;

import android.os.Parcel;
import android.os.Parcelable;

import de.handler.mobile.android.fairmondo.rest.json.model.article.donation.FairmondoOrganization;

/**
 * Subclass of article
 */
public class FairmondoDonation implements Parcelable {
    private int percent;
    private FairmondoOrganization organization;

    public FairmondoOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(FairmondoOrganization organization) {
        this.organization = organization;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
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

    public FairmondoDonation() {
    }

    private FairmondoDonation(Parcel in) {
        this.percent = in.readInt();
        this.organization = in.readParcelable(FairmondoOrganization.class.getClassLoader());
    }

    public static final Parcelable.Creator<FairmondoDonation> CREATOR = new Parcelable.Creator<FairmondoDonation>() {
        public FairmondoDonation createFromParcel(Parcel source) {
            return new FairmondoDonation(source);
        }

        public FairmondoDonation[] newArray(int size) {
            return new FairmondoDonation[size];
        }
    };
}
