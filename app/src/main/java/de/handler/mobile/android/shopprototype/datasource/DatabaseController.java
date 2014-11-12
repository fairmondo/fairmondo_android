package de.handler.mobile.android.shopprototype.datasource;

import android.content.Context;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.datasource.database.Category;
import de.handler.mobile.android.shopprototype.datasource.database.SearchSuggestion;
import de.handler.mobile.android.shopprototype.rest.json.Article;

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
    ShopApp app;

    /**
     * Category methods
     */
    public List<Category> getCategories() {
        return app.getDaoSession().getCategoryDao().loadAll();
    }

    public void setCategories(ArrayList<Category> categories) {
        for (Category category: categories) {
            app.getDaoSession().getCategoryDao().insertOrReplace(category);
        }
    }

    public void setSearchSuggestions(ArrayList<Article> products, ArrayList<Category> categories) {
        for (Article article : products) {
            for (Category category : categories) {
                SearchSuggestion searchSuggestion = new SearchSuggestion(category.getId(), article.getTitle(), "in " + category.getName());
                app.getDaoSession().getSearchSuggestionDao().insertOrReplace(searchSuggestion);
            }
        }
    }
}
