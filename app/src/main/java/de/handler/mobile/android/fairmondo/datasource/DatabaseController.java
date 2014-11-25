package de.handler.mobile.android.fairmondo.datasource;

import android.content.Context;
import android.database.Cursor;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.datasource.database.Category;
import de.handler.mobile.android.fairmondo.datasource.database.Product;
import de.handler.mobile.android.fairmondo.datasource.database.SearchSuggestion;
import de.handler.mobile.android.fairmondo.rest.json.Article;

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

    public Category getCategory(String categoryString) {
        List<Category> categoryList = app.getDaoSession().getCategoryDao().queryRaw("NAME = " + categoryString, "");

        if (categoryList != null && categoryList.size() > 0) {
            return categoryList.get(0);
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
    public void setSearchSuggestions(ArrayList<Article> products, String categoryString) {
        for (Article article : products) {
            if (article != null) {
                SearchSuggestion searchSuggestion = new SearchSuggestion();
                searchSuggestion.setSuggest_text_1(article.getTitle());
                searchSuggestion.setSuggest_text_2(categoryString);
                app.getDaoSession().getSearchSuggestionDao().insertOrReplace(searchSuggestion);
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
    public void setProducts(ArrayList<Article> articles) {

        for (Article article : articles) {
            Product product = new Product();

            product.setId(article.getId().longValue());
            product.setSlug(article.getSlug());
            product.setTitleImageUrl(article.getTitle_image_url());
            product.setHtmlUrl(article.getHtml_url());
            product.setTitle(article.getTitle());
            product.setPriceCents(article.getPrice_cents());

            product.setTagCondition(article.getTags().getCondition());
            product.setTagFair(article.getTags().getFair());
            product.setTagEcologic(article.getTags().getEcologic());
            product.setTagSmallAndPrecious(article.getTags().getSmall_and_precious());
            product.setTagBorrowable(article.getTags().getBorrowable());
            product.setTagSwappable(article.getTags().getSwappable());

            product.setSellerNickname(article.getSeller().getNickname());
            product.setSellerLegalEntity(article.getSeller().getLegal_entity());
            product.setSellerHtmlUrl(article.getSeller().getHtml_url());

            product.setDonation(article.getDonation().getPercent()
                    + article.getDonation().getOrganization().getName());

            app.getDaoSession().getProductDao().insertOrReplace(product);
        }
    }

    public List<Product> getProducts() {
        return app.getDaoSession().getProductDao().loadAll();
    }



}
