package de.handler.mobile.android.fairmondo.data.businessobject.product;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Categories returned with a product.
 */
@Parcel
public class FairmondoCategory {
    protected String id;
    protected String name;
    protected String slug;
    protected List<String> ancestors = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public List<String> getAncestors() {
        return ancestors;
    }
}
