package de.handler.mobile.android.shopprototype.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.ui.adapter.Product;
import de.handler.mobile.android.shopprototype.ui.fragments.SearchResultFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.SearchResultFragment_;

/**
 * Presents the Search Results
 */
@EActivity(R.layout.activity_search)
public class SearchableActivity extends AbstractActivity implements OnSearchResultListener {


    @AfterInject
    public void overlayActionBar() {
        // Request Action Bar overlay before setting content view a.k.a. before @AfterViews
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }

    @AfterViews
    public void init() {
        this.setupActionBar();

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            this.sendFakeProduct(query);

            //this.searchProducts(query);
        }
    }


    private void sendFakeProduct(String query) {
        ArrayList<String> tags = new ArrayList<String>(4);
        tags.add("Musik");
        tags.add("Spielzeug");
        tags.add("Kind");
        tags.add("Kassette");

        ArrayList<Product> results = new ArrayList<Product>();
        for (int i = 0; i < 50; i++) {
            Product product = new Product(
                    (long) i,
                    "http://i.ebayimg.com/t/Kinder-Kassettenrekorder-Bontempi-Recorder-/00/s/MTIwMFgxNjAw/z/nDQAAOSwY45UQAet/$_35.JPG",
                    "CD- & Kassettenrekorder",
                    "Kinder-Kassettenrekorder Bontempi Recorder",
                    "gut funktionierender Kasettenrecorder, Lieferung ohne Netzkabel und ohne Batterien, benötigt 4 Baby ( C ) Zellen",
                    Double.valueOf("12.50"),
                    tags);

            results.add(i, product);
        }

        onProductsSearchResponse(results);
    }


    @Background
    public void searchProducts(String query) {
        Log.d(getLocalClassName().toUpperCase(),
                "Search string: \"" + query + "\" successfully transferred to SearchableActivity");
        // TODO: search here
    }

    @Override
    public void onProductsSearchResponse(ArrayList<Product> products) {
        SearchResultFragment searchResultFragment = new SearchResultFragment_();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SearchResultFragment.SEARCH_RESULT_EXTRA, products);
        searchResultFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_search_result_container, searchResultFragment)
                .commit();
    }
}
