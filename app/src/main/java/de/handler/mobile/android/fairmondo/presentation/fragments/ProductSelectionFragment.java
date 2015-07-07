package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

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
    RecyclerView recyclerView;

    @ViewById(R.id.fragment_product_selection_empty)
    TextView textViewEmpty;

    @App
    FairmondoApp app;

    private List<Product> mProducts;

    @AfterViews
    public void init() {
        mProducts = Parcels.unwrap(mProductsParcelable);

        // Always set up RecyclerView as otherwise there will be an error
        this.setupRecyclerView();
        if (mProducts != null) {
            textViewEmpty.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        recyclerView.setAdapter(new ImageAdapter(getActivity(), mProducts));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
