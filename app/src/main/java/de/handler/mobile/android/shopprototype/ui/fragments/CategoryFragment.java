package de.handler.mobile.android.shopprototype.ui.fragments;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.datasource.database.Category;
import de.handler.mobile.android.shopprototype.interfaces.OnCategoriesListener;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.rest.RestController;

/**
 * Fragment showing a list of categories
 */
@EFragment(R.layout.fragment_category)
public class CategoryFragment extends ListFragment {

    @Bean
    RestController restController;

    @App
    ShopApp app;

    public static final String CATEGORIES_ARRAY_LIST_EXTRA = "categories_array_list_extra";
    private ArrayList<Category> mCategories = new ArrayList<Category>();
    private Activity mActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @AfterInject
    public void initRestController() {
        restController.setCategoriesListener((OnCategoriesListener) mActivity);
        restController.setProductListener((OnSearchResultListener) mActivity);
    }

    @AfterViews
    public void init() {
        mCategories = getArguments().getParcelableArrayList(CATEGORIES_ARRAY_LIST_EXTRA);
        ArrayList<String> categoryStrings = new ArrayList<String>(mCategories.size());
        for (Category category : mCategories) {
            categoryStrings.add(category.getName());
        }

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, categoryStrings));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // It has to be position + 1 as all categories is on first position
        if (position > 0) {
            app.setLastCategory(mCategories.get(position+1).getId().intValue());
            restController.getSubCategories(app.getLastCategory());
        } else {
            // if user selects "all categories" - position 0
            restController.getProduct("", app.getLastCategory());
        }
    }
}
