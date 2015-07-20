package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.adapter.ImageAdapter;

/**
 * Displays all categories for products.
 */
@EFragment(R.layout.fragment_product_selection)
public class ProductSelectionFragment extends Fragment {
    @FragmentArg
    Parcelable mProductsParcelable;

    @ViewById(R.id.fragment_product_category_gridview)
    RecyclerView mRecyclerView;

    @ViewById(R.id.fragment_product_selection_empty)
    TextView mTextViewEmpty;

    @App
    FairmondoApp mApp;

    @AfterViews
    public void init() {
        final List<Product> products = Parcels.unwrap(mProductsParcelable);

        // Always set up RecyclerView as otherwise there will be an error
        this.setupRecyclerView(products);
        if (null == products) {
            mTextViewEmpty.setVisibility(View.VISIBLE);
        } else {
            mTextViewEmpty.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView(@Nullable List<Product> products) {
        //final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        if (null == products) {
            products = new ArrayList<>();
        }
        mRecyclerView.setAdapter(new ImageAdapter(getActivity(), products));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
