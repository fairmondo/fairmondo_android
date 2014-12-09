package de.handler.mobile.android.fairmondo.rest.json;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.rest.json.model.article.FairmondoCategories;
import de.handler.mobile.android.fairmondo.rest.json.model.article.FairmondoDonation;
import de.handler.mobile.android.fairmondo.rest.json.model.article.FairmondoSeller;
import de.handler.mobile.android.fairmondo.rest.json.model.article.FairmondoTag;
import de.handler.mobile.android.fairmondo.rest.json.model.article.FairmondoThumbnails;
import de.handler.mobile.android.fairmondo.rest.json.model.article.FairmondoTitleImage;

/**
 * Model for Rest Communication
 * Represents the structure returned
 * as json by Fairmondo server
 */
public class Article implements Parcelable {
    private Integer id;

    private String slug;
    private String title_image_url;
    private String html_url;
    private String title;

    private String fair_percent_html;
    private String terms;
    private String content;
    private String payment_html;
    private String transport_html;
    private String commendation_html;

    private Integer price_cents = -1;
    private Integer quantity_available = -1;
    private Integer vat = -1;
    private Integer basic_price_cents = -1;
    private String basic_price_amount;

    private FairmondoTitleImage title_image;

    private ArrayList<FairmondoThumbnails> thumbnails;
    private FairmondoTag tags;
    private ArrayList<FairmondoCategories> categories;
    private FairmondoSeller seller;
    private FairmondoDonation donation;


    public Article() {
    }


    public String getPayment_html() {
        return payment_html;
    }

    public void setPayment_html(String payment_html) {
        this.payment_html = payment_html;
    }

    public String getTransport_html() {
        return transport_html;
    }

    public void setTransport_html(String transport_html) {
        this.transport_html = transport_html;
    }

    public String getCommendation_html() {
        return commendation_html;
    }

    public void setCommendation_html(String commendation_html) {
        this.commendation_html = commendation_html;
    }

    public String getTerms_html() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getContent_html() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle_image_url() {
        return title_image_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice_cents() {
        return price_cents;
    }

    public Integer getVat() {
        return vat;
    }

    public Integer getBasic_price_cents() {
        return basic_price_cents;
    }

    public String getBasic_price_amount() {
        return basic_price_amount;
    }

    public FairmondoTag getTags() {
        return tags;
    }

    public FairmondoSeller getSeller() {
        return seller;
    }

    public FairmondoDonation getDonation() {
        return donation;
    }


    public ArrayList<FairmondoThumbnails> getThumbnails() {
        return thumbnails;
    }

    public ArrayList<FairmondoCategories> getCategories() {
        return categories;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTitle_image_url(String title_image_url) {
        this.title_image_url = title_image_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice_cents(int price_cents) {
        this.price_cents = price_cents;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public void setBasic_price_cents(int basic_price_cents) {
        this.basic_price_cents = basic_price_cents;
    }

    public void setBasic_price_amount(String basic_price_amount) {
        this.basic_price_amount = basic_price_amount;
    }

    public void setTags(FairmondoTag tags) {
        this.tags = tags;
    }

    public void setSeller(FairmondoSeller seller) {
        this.seller = seller;
    }

    public void setDonation(FairmondoDonation donation) {
        this.donation = donation;
    }

    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }


    public FairmondoTitleImage getTitle_image() {
        return title_image;
    }

    public void setTitle_image(FairmondoTitleImage title_image) {
        this.title_image = title_image;
    }

    public String getFair_percent_html() {
        return fair_percent_html;
    }

    public void setFair_percent_html(String fair_percent_html) {
        this.fair_percent_html = fair_percent_html;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.slug);
        dest.writeString(this.title_image_url);
        dest.writeString(this.html_url);
        dest.writeString(this.title);
        dest.writeString(this.fair_percent_html);
        dest.writeString(this.terms);
        dest.writeString(this.content);
        dest.writeString(this.payment_html);
        dest.writeString(this.transport_html);
        dest.writeString(this.commendation_html);
        dest.writeInt(this.price_cents);
        dest.writeInt(this.quantity_available);
        dest.writeInt(this.vat);
        dest.writeInt(this.basic_price_cents);
        dest.writeString(this.basic_price_amount);
        dest.writeParcelable(this.title_image, 0);
        dest.writeTypedList(this.thumbnails);
        dest.writeParcelable(this.tags, 0);
        dest.writeTypedList(this.categories);
        dest.writeParcelable(this.seller, 0);
        dest.writeParcelable(this.donation, 0);
    }

    private Article(Parcel in) {
        categories = new ArrayList<FairmondoCategories>();
        thumbnails = new ArrayList<FairmondoThumbnails>();

        this.id = in.readInt();
        this.slug = in.readString();
        this.title_image_url = in.readString();
        this.html_url = in.readString();
        this.title = in.readString();
        this.fair_percent_html = in.readString();
        this.terms = in.readString();
        this.content = in.readString();
        this.payment_html = in.readString();
        this.transport_html = in.readString();
        this.commendation_html = in.readString();
        this.price_cents = in.readInt();
        this.quantity_available = in.readInt();
        this.vat = in.readInt();
        this.basic_price_cents = in.readInt();
        this.basic_price_amount = in.readString();
        this.title_image = in.readParcelable(FairmondoTitleImage.class.getClassLoader());
        in.readTypedList(thumbnails, FairmondoThumbnails.CREATOR);
        this.tags = in.readParcelable(FairmondoTag.class.getClassLoader());
        in.readTypedList(categories, FairmondoCategories.CREATOR);
        this.seller = in.readParcelable(FairmondoSeller.class.getClassLoader());
        this.donation = in.readParcelable(FairmondoDonation.class.getClassLoader());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
