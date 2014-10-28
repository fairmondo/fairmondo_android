package de.handler.mobile.android.shopprototype.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.rest.RestController;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.ui.fragments.SearchResultFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.SearchResultFragment_;

/**
 * Presents the Search Results
 */
@EActivity(R.layout.activity_search)
public class SearchableActivity extends AbstractActivity implements OnSearchResultListener {

    public static final String QUERY_STRING_EXTRA = "query_string_extra";


    @App
    ShopApp app;

    @Bean
    RestController restController;


    @AfterInject
    public void overlayActionBar() {
        // Request Action Bar overlay before setting content view a.k.a. before @AfterViews
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }

    @AfterInject
    public void initRestController() {
        restController.setListener(this);
    }



    @AfterViews
    public void init() {
        this.setupActionBar();

        String query;
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        } else {
            query = getIntent().getStringExtra(QUERY_STRING_EXTRA);
        }

        this.searchProducts(query);
    }


    private void searchProducts(String query) {
        if (app.isConnected()) {
            restController.getProduct(query);
        } else {
            Toast.makeText(this, getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProductsSearchResponse(ArrayList<Article> articles) {
        SearchResultFragment searchResultFragment = new SearchResultFragment_();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SearchResultFragment.SEARCH_RESULT_EXTRA, articles);
        searchResultFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_search_result_container, searchResultFragment)
                .commit();
    }
}
