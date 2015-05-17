package de.handler.mobile.android.fairmondo.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment_;

/**
 * Presents the Search Results.
 */
@EActivity(R.layout.activity_search)
public class SearchableActivity extends AbstractActivity implements OnSearchResultListener {
    public static final String QUERY_STRING_EXTRA = "query_string_extra";

    @App
    FairmondoApp app;

    @Bean
    RestCommunicator restController;

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

        // TODO: do something with the entered search
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
        ProductSelectionFragment searchResultFragment = new ProductSelectionFragment_();
        List<Product> productList = null;
        if (products != null) {
            productList = new ArrayList<>(products);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductSelectionFragment.SELECTION_ARRAY_LIST_EXTRA, Parcels.wrap(List.class, productList));
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
     * ActionBar settings.
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
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
