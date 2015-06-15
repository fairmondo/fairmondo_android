package de.handler.mobile.android.fairmondo.presentation.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.data.datasource.SearchSuggestionProvider;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment_;

/**
 * Presents the Search Results.
 */
@EActivity(R.layout.activity_search)
@OptionsMenu(R.menu.search)
public class SearchableActivity extends AbstractActivity implements OnSearchResultListener {
    @App
    FairmondoApp app;

    @Bean
    RestCommunicator restController;

    @AfterInject
    public void initRestController() {
        restController.setProductListener(this);
    }

    @ViewById(R.id.activity_search_toolbar)
    Toolbar toolbar;

    @AfterViews
    void init() {
        ActionBar actionBar = this.setupActionBar(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.processSearch(getIntent());
    }

    private void processSearch(final Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            // Store in Search Suggestion Provider
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            this.searchProducts(query);
        }
    }

    private void searchProducts(final String query) {
        if (app.isConnected()) {
            if (query != null) {
                restController.getProducts(query);
            } else {
                this.finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProductsSearchResponse(final List<Product> products) {
        ProductSelectionFragment searchResultFragment = ProductSelectionFragment_.builder().mProductsParcelable(Parcels.wrap(List.class, products)).build();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_search_result_container, searchResultFragment).commit();
    }

    @OptionsItem(R.id.action_settings)
    void openSettings() {
        // TODO to implement
    }
}
