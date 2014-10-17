package de.handler.mobile.android.shopprototype.ui.fragments;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity;
import de.handler.mobile.android.shopprototype.ui.ProductGalleryActivity_;
import de.handler.mobile.android.shopprototype.ui.adapter.Product;

/**
 * Displays all categories for products
 */
@EFragment(R.layout.fragment_category)
public class CategoryFragment extends ListFragment implements OnSearchResultListener {

    public static final String CATEGORY_ARRAY_LIST_EXTRA = "category_string_array_list_extra";

    private int mPosition;
    private ArrayList<String> mCategories;


    @ViewById(android.R.id.list)
    ListView listView;


    @AfterViews
    public void init() {
        mCategories = getArguments().getStringArrayList(CATEGORY_ARRAY_LIST_EXTRA);
        // TODO create a list adapter and assign categories list to constructor
        // TODO: set adapter with setAdapter();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1,
                mCategories);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        mPosition = position;
        this.getProducts(mCategories);
        this.getFakeProducts();
    }


    @Background
    public void getProducts(ArrayList<String> categories) {
        // TODO: send search to server
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

        onSearchResult(results);
    }

    @Override
    public void onSearchResult(ArrayList<Product> products) {
        Intent intent = new Intent(getActivity(), ProductGalleryActivity_.class);
        intent.putExtra(ProductGalleryActivity.PAGER_POSITION_EXTRA, mPosition);
        intent.putParcelableArrayListExtra(ProductGalleryActivity.PRODUCT_ARRAY_LIST_EXTRA, products);

        startActivity(intent);
    }
}
