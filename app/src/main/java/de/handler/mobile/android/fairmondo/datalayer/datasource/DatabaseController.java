package de.handler.mobile.android.fairmondo.datalayer.datasource;

import android.content.Context;
import android.database.Cursor;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.datalayer.datasource.database.Category;
import de.handler.mobile.android.fairmondo.datalayer.datasource.database.Product;
import de.handler.mobile.android.fairmondo.datalayer.datasource.database.SearchSuggestion;

/**
 * Provides the methods to work with database
 */
@EBean
public class DatabaseController {

    private Context mContext;
    public DatabaseController(Context mContext) {
        this.mContext = mContext;
    }

    @App
    FairmondoApp app;

    /**
     * Category methods
     */
    public List<Category> getCategories() {
        return app.getDaoSession().getCategoryDao().loadAll();
    }

    public FairmondoCategory getCategory(String categoryString) {
        List<Category> categoryList = app.getDaoSession().getCategoryDao().queryRaw("NAME = " + categoryString, "");

        if (categoryList != null && categoryList.size() > 0) {
            return app.getModelMapper().map(categoryList.get(0), FairmondoCategory.class);
        } else {
            return null;
        }
    }


    public void setCategories(ArrayList<Category> categories) {
        for (Category category: categories) {
            app.getDaoSession().getCategoryDao().insertOrReplace(category);
        }
    }

    @Background
    public void setSearchSuggestions(ArrayList<de.handler.mobile.android.fairmondo.datalayer.businessobject.Product> products, String categoryString) {
        if (products != null) {
            for (de.handler.mobile.android.fairmondo.datalayer.businessobject.Product product : products) {
                if (product != null) {
                    SearchSuggestion searchSuggestion = new SearchSuggestion();
                    searchSuggestion.setSuggest_text_1(product.getTitle());
                    searchSuggestion.setSuggest_text_2(categoryString);
                    app.getDaoSession().getSearchSuggestionDao().insertOrReplace(searchSuggestion);
                }
            }
        }
    }


    public SearchSuggestion getSearchSuggestions(Cursor cursor) {
        return app.getDaoSession().getSearchSuggestionDao().readEntity(cursor, 0);
    }


    public List<SearchSuggestion> getSearchSuggestions() {
        return app.getDaoSession().getSearchSuggestionDao().loadAll();
    }

    @Background
    public void setProducts(ArrayList<de.handler.mobile.android.fairmondo.datalayer.businessobject.Product> articles) {

        for (de.handler.mobile.android.fairmondo.datalayer.businessobject.Product product : articles) {
            Product productDAO = app.getModelMapper().map(product, Product.class);
            app.getDaoSession().getProductDao().insertOrReplace(productDAO);
        }
    }

    public List<Product> getProducts() {
        return app.getDaoSession().getProductDao().loadAll();
    }
}
