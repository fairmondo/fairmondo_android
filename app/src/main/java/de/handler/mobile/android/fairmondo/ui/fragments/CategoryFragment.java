package de.handler.mobile.android.fairmondo.ui.fragments;

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

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datalayer.datasource.database.Category;
import de.handler.mobile.android.fairmondo.datalayer.interfaces.OnCategoriesListener;
import de.handler.mobile.android.fairmondo.datalayer.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.networklayer.rest.RestController;

/**
 * Fragment showing a list of categories
 */
@EFragment(R.layout.fragment_category)
public class CategoryFragment extends ListFragment {

    @Bean
    RestController restController;

    @App
    FairmondoApp app;

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
        // Get categories which have been added to the fragment bundle in the calling class
        mCategories = getArguments().getParcelableArrayList(CATEGORIES_ARRAY_LIST_EXTRA);

        // Extract the title of each category
        ArrayList<String> categoryStrings = new ArrayList<String>(mCategories.size());
        for (Category category : mCategories) {
            categoryStrings.add(category.getName());
        }

        // init the list adapter with those strings
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, categoryStrings));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        if (position > 0) {
            // set last category in application context to know
            // which category was last pressed and get the products
            // if the user wishes it
            app.setLastCategory(mCategories.get(position));
            restController.getSubCategories(app.getLastCategory().getId().intValue());
        } else {
            // if user selects "all products" - position 0 - get the products
            restController.getProduct("", app.getLastCategory().getId().intValue());
        }
    }
}
