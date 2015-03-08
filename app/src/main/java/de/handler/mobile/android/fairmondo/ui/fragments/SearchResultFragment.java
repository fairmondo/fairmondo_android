package de.handler.mobile.android.fairmondo.ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.Product;
import de.handler.mobile.android.fairmondo.ui.activities.ProductGalleryActivity;
import de.handler.mobile.android.fairmondo.ui.activities.ProductGalleryActivity_;
import de.handler.mobile.android.fairmondo.ui.adapter.ImageAdapter;

/**
 * Displays search results in a grid view
 */
@EFragment(R.layout.fragment_search_result)
public class SearchResultFragment extends Fragment implements RecyclerView.OnItemTouchListener {
    public static final String SEARCH_RESULT_EXTRA = "search_result_array_list_extra";
    private ArrayList<Product> mProducts;
    private GestureDetector mGestureDetector;

    @ViewById(R.id.fragment_search_gridview)
    RecyclerView recyclerView;

    @AfterViews
    public void init() {
        // Get Bundle
        mProducts = getArguments().getParcelableArrayList(SEARCH_RESULT_EXTRA);
        this.setupRecyclerView();
        // TODO add filters to search results
    }

    private void setupRecyclerView() {
        mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        recyclerView.setAdapter(new ImageAdapter(getActivity(), mProducts));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Methods for click events on product
     */
    private void startProductGallery(int position) {
        Intent intent = new Intent(getActivity(), ProductGalleryActivity_.class);
        intent.putExtra(ProductGalleryActivity.PAGER_POSITION_EXTRA, position);
        intent.putParcelableArrayListExtra(ProductGalleryActivity.PRODUCT_ARRAY_LIST_EXTRA, mProducts);
        startActivity(intent);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (childView != null && mGestureDetector.onTouchEvent(motionEvent)) {
            startProductGallery(recyclerView.getChildPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }
}
