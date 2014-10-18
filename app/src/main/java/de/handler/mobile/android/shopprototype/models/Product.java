package de.handler.mobile.android.shopprototype.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Contains the important information about a product
 */
public class Product implements Parcelable {

    private Long id;
    private String imageUrl;
    private String category;
    private String title;
    private String description;
    private Double price;
    private ArrayList<String> tags;

    public Product(String imageUrl, String category, String title, String description, Double price, ArrayList<String> tags) {
        this.imageUrl = imageUrl;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.tags = tags;
    }

    public Product(Long id, String imageUrl, String category, String title, String description, Double price, ArrayList<String> tags) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.tags = tags;
    }


    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }


    public Product(Parcel dest) {
        id = dest.readLong();
        imageUrl = dest.readString();
        category = dest.readString();
        title = dest.readString();
        description = dest.readString();
        price = dest.readDouble();
        tags = dest.createStringArrayList();
    }


    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(imageUrl);
        dest.writeString(category);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeStringList(tags);
    }

}
