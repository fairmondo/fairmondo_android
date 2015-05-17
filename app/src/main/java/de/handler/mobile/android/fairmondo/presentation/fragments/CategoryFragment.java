package de.handler.mobile.android.fairmondo.presentation.fragments;

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
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.data.interfaces.OnCategoriesListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;

/**
 * Fragment showing a list of categories.
 */
@EFragment(R.layout.fragment_category)
public class CategoryFragment extends ListFragment {
    public static final String CATEGORIES_ARRAY_LIST_EXTRA = "categories_array_list_extra";

    @Bean
    RestCommunicator restController;

    @App
    FairmondoApp app;

    private ArrayList<FairmondoCategory> mCategories = new ArrayList<>();
    private Activity mActivity;


    @Override
    public void onAttach(final Activity activity) {
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
        mCategories = Parcels.unwrap(getArguments().getParcelable(CATEGORIES_ARRAY_LIST_EXTRA));

        // Extract the title of each category
        List<String> categoryStrings = new ArrayList<>(mCategories.size());
        categoryStrings.add(getString(R.string.all_products));
        for (FairmondoCategory category : mCategories) {
            categoryStrings.add(category.getName());
        }

        // init the list adapter with those strings
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, categoryStrings));
    }

    @Override
    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        if (position > 0) {
            // set last category in application context to know
            // which category was last pressed and get the products
            // if the user wishes it
            app.setLastCategory(mCategories.get(position));
            restController.getSubCategories(app.getLastCategory().getId());
        } else {
            // if user selects "all products" - position 0 - get the products
            restController.getProducts("", app.getLastCategory().getId());
        }
    }
}
