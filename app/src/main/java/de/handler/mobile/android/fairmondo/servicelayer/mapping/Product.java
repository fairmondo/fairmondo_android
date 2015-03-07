package de.handler.mobile.android.fairmondo.servicelayer.mapping;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.networklayer.rest.dto.Article;
import de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article.*;

/**
 * Product class describing what is needed in ui
 */
public class Product {

    private final Article article;

    public Product(Article article) {
        this.article = article;
    }

    public String getId() {
        return article.id;
    }

    public String getSlug() {
        return article.slug;
    }

    public String getTitleImageUrl() {
        return article.title_image_url;
    }

    public String getHtmlUrl() {
        return article.html_url;
    }

    public String getTitle() {
        return article.title;
    }

    public String getFairPercentHtml() {
        return article.fair_percent_html;
    }

    public String getTerms() {
        return article.terms;
    }

    public String getContent() {
        return article.content;
    }

    public String getPaymentHtml() {
        return article.payment_html;
    }

    public String getTransportHtml() {
        return article.transport_html;
    }

    public String getCommendationHtml() {
        return article.commendation_html;
    }

    public Integer getPriceCents() {
        return article.price_cents;
    }

    public Integer getQuantityAvailable() {
        return article.quantity_available;
    }

    public Integer getVat() {
        return article.vat;
    }

    public Integer getBasicPriceCents() {
        return article.basic_price_cents;
    }

    public String getBasicPriceAmount() {
        return article.basic_price_amount;
    }

    public FairmondoTitleImage getTitleImage() {
        return new FairmondoTitleImage(article.title_image);
    }

    public ArrayList<FairmondoThumbnail> getThumbnails() {
        ArrayList<FairmondoThumbnail> fairmondoThumbnails = new ArrayList<>(article.thumbnails.size());
        for (de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article.FairmondoThumbnail ft : article.thumbnails) {
            fairmondoThumbnails.add(new FairmondoThumbnail(ft));
        }
        return fairmondoThumbnails;
    }

    public FairmondoTag getTags() {
        return new FairmondoTag(article.tags);
    }

    public ArrayList<FairmondoCategory> getCategories() {
        ArrayList<FairmondoCategory> fairmondoCategories = new ArrayList<>(article.categories.size());
        for (de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article.FairmondoCategory c : article.categories) {
            fairmondoCategories.add(new FairmondoCategory(c));
        }
        return fairmondoCategories;
    }

    public FairmondoSeller getSeller() {
        return new FairmondoSeller(article.seller);
    }

    public FairmondoDonation getDonation() {
        return new FairmondoDonation(article.donation);
    }
}
