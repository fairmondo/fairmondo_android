package de.handler.mobile.android.fairmondo.ui.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datasource.DatabaseController;
import de.handler.mobile.android.fairmondo.datasource.SearchSuggestionProvider;
import de.handler.mobile.android.fairmondo.datasource.database.Category;
import de.handler.mobile.android.fairmondo.datasource.database.SearchSuggestion;
import de.handler.mobile.android.fairmondo.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.rest.RestController;
import de.handler.mobile.android.fairmondo.rest.json.Article;
import de.handler.mobile.android.fairmondo.ui.fragments.ProductSelectionFragment;
import de.handler.mobile.android.fairmondo.ui.fragments.ProductSelectionFragment_;

/**
 * Presents the Search Results
 */
@EActivity(R.layout.activity_search)
public class SearchableActivity extends AbstractActivity implements OnSearchResultListener {

    public static final String QUERY_STRING_EXTRA = "query_string_extra";


    @App
    FairmondoApp app;

    @Bean
    RestController restController;

    @Bean
    DatabaseController databaseController;


    @AfterInject
    public void initRestController() {
        restController.setProductListener(this);
    }



    @AfterViews
    public void init() {
        ActionBar actionBar = this.setupActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String query;
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        String action = intent.getAction();

        // TODO why does this not match? The strings are equal
        // Check which action is send with the intent and react appropriate
        switch (action) {
            case Intent.ACTION_VIEW:
                // Should load when clicked on a search suggestion
                Uri data = intent.getData();
                this.showResult(data);
                break;
            case Intent.ACTION_SEARCH:
                query = intent.getStringExtra(SearchManager.QUERY);
                this.searchProducts(query);
                break;
            default:
                query = intent.getStringExtra(QUERY_STRING_EXTRA);
                Log.d(this.getLocalClassName(), action);
                Log.d(this.getLocalClassName(), intent.getDataString());
                this.searchProducts(query);
                break;
        }
    }


    private void showResult(Uri data) {
        if (app.isConnected()) {
            SearchSuggestionProvider searchSuggestionProvider = new SearchSuggestionProvider();
            Cursor cursor = searchSuggestionProvider.query(data, null, null, null, null);
            SearchSuggestion searchSuggestion = databaseController.getSearchSuggestions(cursor);
            Category category = databaseController.getCategory(searchSuggestion.getSuggest_text_2());
            restController.getProduct(searchSuggestion.getSuggest_text_1(), category.getId().intValue());
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }


    private void searchProducts(String query) {
        if (app.isConnected()) {
            if (query != null) {
                restController.getProduct(query);
            } else {
                this.finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onProductsSearchResponse(ArrayList<Article> articles) {
        ProductSelectionFragment searchResultFragment = new ProductSelectionFragment_();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ProductSelectionFragment.SELECTION_ARRAY_LIST_EXTRA, articles);
        searchResultFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_search_result_container, searchResultFragment)
                .commit();
    }


    @Override
    public void showProgressBar() {

    }


    @Override
    public void hideProgressBar() {

    }


    /**
     * ActionBar settings
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                this.openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {
        // TODO to implement
    }
}
