package de.handler.mobile.android.fairmondo.network.dto;

import de.handler.mobile.android.fairmondo.network.dto.model.article.FairmondoCategory;
import de.handler.mobile.android.fairmondo.network.dto.model.article.FairmondoDonation;
import de.handler.mobile.android.fairmondo.network.dto.model.article.FairmondoSeller;
import de.handler.mobile.android.fairmondo.network.dto.model.article.FairmondoTag;
import de.handler.mobile.android.fairmondo.network.dto.model.article.FairmondoThumbnail;
import de.handler.mobile.android.fairmondo.network.dto.model.article.FairmondoTitleImage;

/**
 * DTO for Rest Communication
 * Represents the structure returned
 * as json by Fairmondo server
 */
public class Product {
    public Long id;
    public String slug;
    public String title;
    public int price_cents;
    public int quantity_available;
    public int vat;
    public FairmondoTag tags;
    public FairmondoDonation donation;
    public FairmondoCategory[] categories;
    public String title_image_url;
    public FairmondoTitleImage title_image;
    public FairmondoThumbnail[] thumbnails;
    public FairmondoSeller seller;
    public String content;
    public String payment_html;
    public String transport_html;
    public String commendation_html;
    public String html_url;
}
