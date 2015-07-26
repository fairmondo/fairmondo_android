package de.handler.mobile.android.fairmondo.presentation.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.data.datasource.SearchSuggestionProvider;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.controller.ProgressController;

/**
 * Presents the Search Results.
 */
@EActivity(R.layout.activity_search)
public class SearchableActivity extends AbstractActivity implements OnSearchResultListener {
    @Bean
    ProgressController mProgressController;

    @App
    FairmondoApp mApp;

    @Bean
    RestCommunicator mRestCommunicator;

    @ViewById(R.id.activity_search_toolbar)
    Toolbar mToolbar;

    @AfterInject
    public void initRestController() {
        mRestCommunicator.setProductListener(this);
    }

    @AfterViews
    void init() {
        setHomeUpEnabled(mToolbar);
        this.processSearch(getIntent());
    }

    private void processSearch(@NonNull final Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);

            // Store in Search Suggestion Provider
            final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            this.searchProducts(query);
        }
    }

    private void searchProducts(@Nullable final String query) {
        if (query != null) {
            mRestCommunicator.getProducts(query);
            mProgressController.startProgress(getSupportFragmentManager(), android.R.id.content);
        } else {
            finish();
        }
    }

    @Override
    public void onProductsSearchResponse(final List<Product> products) {
        this.mProgressController.stopProgress();
        this.startSelectionActivity(products);
    }

    private void startSelectionActivity(@Nullable final List<Product> products) {
        ProductSelectionActivity_.intent(this).mProductListParcelable(Parcels.wrap(List.class, products)).start();
        finish();
    }
}
