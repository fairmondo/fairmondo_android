package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.adapter.ImageAdapter;

/**
 * Displays all categories for products.
 */
@EFragment(R.layout.fragment_product_selection)
public class ProductSelectionFragment extends Fragment implements OnSearchResultListener {
    @FragmentArg
    Parcelable mProductsParcelable;

    @App
    FairmondoApp mApp;

    @Bean
    RestCommunicator mRestCommunicator;

    @ViewById(R.id.fragment_product_category_gridview)
    RecyclerView mRecyclerView;

    @ViewById(R.id.fragment_product_selection_empty)
    TextView mTextViewEmpty;

    private boolean mLoading = true;
    private int mCurrentPage = 1;
    private List<Product> mProducts;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int visibleItemCount = mRecyclerView.getLayoutManager().getChildCount();
            int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();
            int lastVisibleItems = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if (mLoading) {
                if ((visibleItemCount + lastVisibleItems) >= totalItemCount) {
                    mLoading = false;
                    mCurrentPage++;
                    String categoryId = "";
                    final FairmondoCategory category = mApp.getLastCategory();
                    if (category != null) {
                        categoryId = category.getId();
                    }
                    mRestCommunicator.getProducts("", categoryId, mCurrentPage);
                }
            }
        }
    };

    @AfterViews
    public void init() {
        mProducts = Parcels.unwrap(mProductsParcelable);
        mRestCommunicator.setProductListener(this);

        // Always set up RecyclerView as otherwise there will be an error
        this.setupRecyclerView(mProducts);
        if (null == mProducts) {
            mTextViewEmpty.setVisibility(View.VISIBLE);
        } else {
            mTextViewEmpty.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView(@Nullable List<Product> products) {
        //final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        if (null == products) {
            products = new ArrayList<>();
        }
        mRecyclerView.setAdapter(new ImageAdapter(getActivity(), products));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onProductsSearchResponse(final List<Product> products) {
        mProducts.addAll(products);
        this.updateAdapter(mProducts);
    }

    @UiThread
    void updateAdapter(@NonNull final List<Product> products) {
        ((ImageAdapter) mRecyclerView.getAdapter()).updateItems(products);
        mLoading = true;
    }
}