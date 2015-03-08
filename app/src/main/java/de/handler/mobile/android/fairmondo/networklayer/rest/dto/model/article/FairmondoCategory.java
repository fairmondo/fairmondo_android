package de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article;

import java.util.List;

/**
 * Categories returned with a product
 */
public class FairmondoCategory {
    public Long id;
    public String name;
    public String slug;
    public List<String> ancestors;
}
