package de.handler.mobile.android.shopprototype.datasource;

import android.content.Context;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.datasource.database.Category;
import de.handler.mobile.android.shopprototype.datasource.database.SearchSuggestion;

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

    public void setSearchSuggestions(ArrayList<Category> categories) {
        for (Category category: categories) {
            SearchSuggestion searchSuggestion = new SearchSuggestion(category.getId(), null, category.getName());
            app.getDaoSession().getSearchSuggestionDao().insertOrReplace(searchSuggestion);
        }
    }
}
