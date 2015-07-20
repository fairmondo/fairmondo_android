package de.handler.mobile.android.fairmondo.network.dto.product;

import java.util.List;

/**
 * Categories returned with a product.
 */
public class FairmondoCategory {
    public String id;
    public String name;
    public String slug;
    public List<String> ancestors;
}
