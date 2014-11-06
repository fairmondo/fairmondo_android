package de.handler.mobile.android.shopprototype.ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity_;
import de.handler.mobile.android.shopprototype.ui.adapter.ImageAdapter;

/**
 * Displays all categories for products
 */
@EFragment(R.layout.fragment_product_selection)
public class ProductSelectionFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String SELECTION_ARRAY_LIST_EXTRA = "selection_string_array_list_extra";

    private int mPosition;
    private ArrayList<Article> mProducts;

    @ViewById(R.id.fragment_product_category_gridview)
    GridView gridView;

    @ViewById(R.id.fragment_product_selection_empty)
    TextView textViewEmpty;


    @AfterViews
    public void init() {
        mProducts = getArguments().getParcelableArrayList(SELECTION_ARRAY_LIST_EXTRA);

        if (mProducts != null) {
            textViewEmpty.setVisibility(View.GONE);
            gridView.setAdapter(new ImageAdapter(getActivity(), mProducts, R.layout.adapter_image_grid_item));
            gridView.setOnItemClickListener(this);
            gridView.setOnItemLongClickListener(this);
        } else {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPosition = position;
        this.startProductGallery();
    }

    private void startProductGallery() {
        Intent intent = new Intent(getActivity(), ProductGalleryActivity_.class);
        intent.putExtra(ProductGalleryActivity.PAGER_POSITION_EXTRA, mPosition);
        intent.putParcelableArrayListExtra(ProductGalleryActivity.PRODUCT_ARRAY_LIST_EXTRA, mProducts);

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
