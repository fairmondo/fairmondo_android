package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.data.interfaces.OnCategoriesListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnClickItemListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.controller.ProgressController;

/**
 * Fragment showing a list of categories.
 */
@EFragment(R.layout.fragment_category)
public class CategoryFragment extends ListFragment {
    @FragmentArg
    Parcelable mCategoriesParcelable;

    @Bean
    ProgressController mProgressController;

    @Bean
    RestCommunicator restController;

    @App
    FairmondoApp app;

    private OnClickItemListener onClickItemListener;
    private Activity mActivity;
    private List<FairmondoCategory> mCategories;

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

        try {
            onClickItemListener = (OnClickItemListener) activity;
        } catch (ClassCastException e) {
            Log.e(getClass().getCanonicalName(), "Activity must implement OnClickItemListener");
        }
    }

    @AfterInject
    public void initRestController() {
        restController.setCategoriesListener((OnCategoriesListener) mActivity);
        restController.setProductListener((OnSearchResultListener) mActivity);
    }

    @AfterViews
    public void init() {
        // Get categories which have been added to the fragment bundle in the calling class
        mCategories = Parcels.unwrap(mCategoriesParcelable);
        // Extract the title of each category
        List<String> categoryStrings = new ArrayList<>(mCategories.size());
        categoryStrings.add(getString(R.string.all_products));
        for (FairmondoCategory category : mCategories) {
            categoryStrings.add(category.getName());
        }

        // init the list adapter with those strings
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, categoryStrings));
    }

    @Override
    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        onClickItemListener.onItemClick();
        mProgressController.startProgress(getFragmentManager(), android.R.id.content);
        if (position > 0) {
            // set last category in application context to know
            // which category was last pressed and get the products
            // if the user wishes it
            app.setLastCategory(mCategories.get(position - 1));
            restController.getSubCategories(app.getLastCategory().getId());
        } else {
            // onAnimationListener.onMinimizeTitleFragment();
            // if user selects "all products" - position 0 - get the products
            if (null == app.getLastCategory()) {
                restController.getProducts("");
            } else {
                restController.getProducts("", app.getLastCategory().getId());
            }
        }
    }
}
