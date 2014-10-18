package de.handler.mobile.android.shopprototype.ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.models.Product;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity_;
import de.handler.mobile.android.shopprototype.ui.adapter.ImageAdapter;

/**
 * Displays search results in a grid view
 */
@EFragment(R.layout.fragment_search_result)
public class SearchResultFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String SEARCH_RESULT_EXTRA = "search_result_array_list_extra";
    private ArrayList<Product> mProducts;

    @ViewById(R.id.fragment_search_gridview)
    GridView gridView;


    @AfterViews
    public void init() {

        // Get Bundle
        mProducts = getArguments().getParcelableArrayList(SEARCH_RESULT_EXTRA);

        gridView.setAdapter(new ImageAdapter(getActivity(), mProducts, R.layout.adapter_image_grid_item));
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
    }

    /**
     * Methods for click events on product
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), ProductGalleryActivity_.class);
        intent.putExtra(ProductGalleryActivity.PAGER_POSITION_EXTRA, position);
        intent.putParcelableArrayListExtra(ProductGalleryActivity.PRODUCT_ARRAY_LIST_EXTRA, mProducts);

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
