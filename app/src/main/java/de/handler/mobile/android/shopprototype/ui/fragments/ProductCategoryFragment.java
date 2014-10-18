package de.handler.mobile.android.shopprototype.ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.models.Product;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity_;
import de.handler.mobile.android.shopprototype.ui.adapter.ImageAdapter;

/**
 * Displays all categories for products
 */
@EFragment(R.layout.fragment_product_category)
public class ProductCategoryFragment extends Fragment implements OnSearchResultListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String CATEGORY_ARRAY_LIST_EXTRA = "category_string_array_list_extra";

    private int mPosition;
    private ArrayList<Product> mProducts;

    @ViewById(R.id.fragment_product_category_progress_bar)
    ProgressBar progressBar;

    @ViewById(R.id.fragment_product_category_gridview)
    GridView gridView;


    @AfterViews
    public void init() {
        String category = getArguments().getString(CATEGORY_ARRAY_LIST_EXTRA);
        this.getProducts(category);
        this.getFakeProducts();
    }


    @Background
    public void getProducts(String category) {
        // TODO: send search to server
        progressBar.setVisibility(View.VISIBLE);
    }

    private void getFakeProducts() {
        ArrayList<String> tags = new ArrayList<String>(4);
        tags.add("Musik");
        tags.add("Spielzeug");
        tags.add("Kind");
        tags.add("Kassette");

        ArrayList<Product> results = new ArrayList<Product>();
        for (int i = 0; i < 50; i++) {
            Product product = new Product(
                    (long) i,
                    "http://i.ebayimg.com/t/Anatomische-Pinzetten-14-16-18-20-25-30-35-oder-40cm-gerade-oder-gebogen-/00/s/ODAwWDgwMA==/$%28KGrHqFHJEIE88cr2Pb+BPZ4dZ4WQ!~~60_35.JPG",
                    "CD- & Kassettenrekorder",
                    "Kinder-Kassettenrekorder Bontempi Recorder",
                    "gut funktionierender Kasettenrecorder, Lieferung ohne Netzkabel und ohne Batterien, benötigt 4 Baby ( C ) Zellen",
                    Double.valueOf("12.50"),
                    tags);

            results.add(i, product);
        }

        onProductsSearchResponse(results);
    }

    @Override
    public void onProductsSearchResponse(ArrayList<Product> products) {
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
