package de.handler.mobile.android.shopprototype.ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.rest.RestController;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity_;
import de.handler.mobile.android.shopprototype.ui.adapter.ImageAdapter;

/**
 * Displays all categories for products
 */
@EFragment(R.layout.fragment_product_category)
public class ProductCategoryFragment extends Fragment implements OnSearchResultListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String CATEGORY_ARRAY_LIST_EXTRA = "category_string_array_list_extra";
    public static final String POSITION_INT_EXTRA = "position_int_extra";

    private int mPosition;
    private ArrayList<Article> mProducts;

    @Bean
    RestController restController;

    @ViewById(R.id.fragment_product_category_progress_bar)
    ProgressBar progressBar;

    @ViewById(R.id.fragment_product_category_gridview)
    GridView gridView;


    @AfterViews
    public void init() {
        String category = getArguments().getString(CATEGORY_ARRAY_LIST_EXTRA);
        // TODO position only as workaround for category id --> change to categoryId
        int position = getArguments().getInt(POSITION_INT_EXTRA);
        this.getProducts(category, position);
    }


    private void getProducts(String category, int position) {
        //TODO get categories from server and use id
        restController.setListener(this);
        restController.getProduct("", position);
        progressBar.setVisibility(View.VISIBLE);
    }


    @UiThread
    @Override
    public void onProductsSearchResponse(ArrayList<Article> products) {
        mProducts = products;
        progressBar.setVisibility(View.GONE);

        gridView.setAdapter(new ImageAdapter(getActivity(), products, R.layout.adapter_image_grid_item));
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
    }

    private void startProductGallery() {
        Intent intent = new Intent(getActivity(), ProductGalleryActivity_.class);
        intent.putExtra(ProductGalleryActivity.PAGER_POSITION_EXTRA, mPosition);
        intent.putParcelableArrayListExtra(ProductGalleryActivity.PRODUCT_ARRAY_LIST_EXTRA, mProducts);

        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPosition = position;
        this.startProductGallery();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
