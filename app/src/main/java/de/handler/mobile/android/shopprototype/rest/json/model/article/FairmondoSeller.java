package de.handler.mobile.android.shopprototype.rest.json.model.article;

import android.os.Parcel;
import android.os.Parcelable;

import de.handler.mobile.android.shopprototype.rest.json.model.article.seller.FairmondoRating;

/**
 * Subclass of article
 */
public class FairmondoSeller implements Parcelable {
    private String nickname;
    private Boolean legal_entity;
    private Boolean ngo;
    private Boolean vacationing;
    private String name;
    private String type;
    private String type_name;
    private String html_url;
    private String image_url;
    private FairmondoRating ratings;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Boolean getNgo() {
        return ngo;
    }

    public void setNgo(Boolean ngo) {
        this.ngo = ngo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVacationing() {
        return vacationing;
    }

    public void setVacationing(Boolean vacationing) {
        this.vacationing = vacationing;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public FairmondoRating getRatings() {
        return ratings;
    }

    public void setRatings(FairmondoRating ratings) {
        this.ratings = ratings;
    }
}
