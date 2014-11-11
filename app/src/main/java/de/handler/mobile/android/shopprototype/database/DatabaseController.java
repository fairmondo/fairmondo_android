package de.handler.mobile.android.shopprototype.database;

import android.content.Context;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.shopprototype.ShopApp;

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


}
