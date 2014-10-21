package de.handler.mobile.android.shopprototype.rest.json.model.article;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Subclass of article
 */
public class FairmondoSeller implements Parcelable {
    private String nickname;
    private Boolean legal_entity;
    private String html_url;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getLegal_entity() {
        return legal_entity;
    }

    public void setLegal_entity(Boolean legal_entity) {
        this.legal_entity = legal_entity;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickname);
        dest.writeValue(this.legal_entity);
        dest.writeString(this.html_url);
    }

    public FairmondoSeller() {
    }

    private FairmondoSeller(Parcel in) {
        this.nickname = in.readString();
        this.legal_entity = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.html_url = in.readString();
    }

    public static final Parcelable.Creator<FairmondoSeller> CREATOR = new Parcelable.Creator<FairmondoSeller>() {
        public FairmondoSeller createFromParcel(Parcel source) {
            return new FairmondoSeller(source);
        }

        public FairmondoSeller[] newArray(int size) {
            return new FairmondoSeller[size];
        }
    };
}
