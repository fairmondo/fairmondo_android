package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.activities.ProductGalleryActivity;
import de.handler.mobile.android.fairmondo.presentation.activities.ProductGalleryActivity_;
import de.handler.mobile.android.fairmondo.presentation.adapter.ImageAdapter;

/**
 * Displays all categories for products.
 */
@EFragment(R.layout.fragment_product_selection)
public class ProductSelectionFragment extends Fragment implements RecyclerView.OnItemTouchListener {
    public static final String SELECTION_ARRAY_LIST_EXTRA = "selection_string_array_list_extra";

    private List<Product> mProducts;
    private GestureDetector mGestureDetector;

    @ViewById(R.id.fragment_product_category_gridview)
    RecyclerView recyclerView;

    @ViewById(R.id.fragment_product_selection_empty)
    TextView textViewEmpty;

    @App
    FairmondoApp app;

    @AfterViews
    public void init() {
        if (getArguments() != null) {
            mProducts = Parcels.unwrap(getArguments().getParcelable(SELECTION_ARRAY_LIST_EXTRA));
        }

        // Always set up RecyclerView as otherwise there will be an error
        this.setupRecyclerView();
        if (mProducts != null) {
            textViewEmpty.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerView() {
        mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(final MotionEvent e) {
                return true;
            }
        });

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        recyclerView.setAdapter(new ImageAdapter(getActivity(), mProducts));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(this);
    }

    private void startProductGallery(final int position, final Bundle animationBundle) {
        Intent intent = new Intent(getActivity(), ProductGalleryActivity_.class);
        intent.putExtra(ProductGalleryActivity.PAGER_POSITION_EXTRA, position);
        //intent.putExtra(ProductGalleryActivity.PRODUCT_ARRAY_LIST_EXTRA, mProducts);
        app.setProducts(mProducts);

        if (animationBundle != null) {
            getActivity().startActivity(intent, animationBundle);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(final RecyclerView recyclerView, final MotionEvent motionEvent) {
        final View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (childView != null && mGestureDetector.onTouchEvent(motionEvent)) {
            final Bundle bundle = ActivityOptionsCompat.makeScaleUpAnimation(childView, 0, 0,
                    childView.getWidth(), childView.getHeight()).toBundle();
            this.startProductGallery(recyclerView.getChildPosition(childView), bundle);
        }
        return false;
    }

    @Override
    public void onTouchEvent(final RecyclerView recyclerView, final MotionEvent motionEvent) {

    }
}
