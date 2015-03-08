package de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article;

import de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article.seller.FairmondoRating;

/**
 * DTO Subclass of article
 */
public class FairmondoSeller {
    public String nickname;
    public boolean legal_entity;
    public boolean ngo;
    public boolean vacationing;
    public String name;
    public String type;
    public String type_name;
    public String html_url;
    public String image_url;
    public FairmondoRating rating;
    public String terms;
}
