package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.activities.ProductGalleryActivity_;
import de.handler.mobile.android.fairmondo.presentation.adapter.ListItemAdapter;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnEndlessScrollListener;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnRecyclerItemClickListener;

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

    private boolean mLoading = true;
    private int mCurrentPage = 1;
    private OnEndlessScrollListener mOnEndlessScrollListener;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int visibleItemCount = mRecyclerView.getLayoutManager().getChildCount();
            int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();
            int lastVisibleItems = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if (mLoading) {
                if ((visibleItemCount + lastVisibleItems) >= totalItemCount && null != mOnEndlessScrollListener) {
                    mLoading = false;
                    mCurrentPage++;
                    mOnEndlessScrollListener.onListEndReached(mCurrentPage);
                }
            }
        }
    };

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        try {
            mOnEndlessScrollListener = (OnEndlessScrollListener) activity;
        } catch (final Exception e) {
            Log.d(getClass().getCanonicalName(), "Your Activity has to implement OnEndlessScrollListener");
            e.printStackTrace();
        }
    }

    @AfterViews
    public void init() {
        List<Product> products = Parcels.unwrap(mProductsParcelable);

        // Always set up RecyclerView as otherwise there will be an error
        this.setupRecyclerView(products);
        this.checkListNull(products);
    }

    private void setupRecyclerView(@Nullable List<Product> products) {
        //final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        if (null == products) {
            products = new ArrayList<>();
        }
        final ListItemAdapter listItemAdapter = new ListItemAdapter(getActivity(), products);
        listItemAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<Integer>() {
            @Override
            public void onRecyclerItemClicked(final View view, final Integer position) {
                final ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, view.getScrollX(), view.getScrollY(), view.getWidth(), view.getHeight());
                ProductGalleryActivity_.intent(getActivity()).mPosition(position).mProductsParcelable(mProductsParcelable).withOptions(options.toBundle()).start();
            }
        });
        mRecyclerView.setAdapter(listItemAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @UiThread
    public void updateAdapter(@Nullable final List<Product> products) {
        this.checkListNull(products);
        if (null != products) {
            ((ListItemAdapter) mRecyclerView.getAdapter()).updateItems(products);
        }
        mLoading = true;
    }

    private void checkListNull(List<Product> products) {
        if (null == products || products.isEmpty()) {
            mTextViewEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mTextViewEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}