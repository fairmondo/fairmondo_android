package de.handler.mobile.android.shopprototype.ui;

import android.app.SearchManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.interfaces.OnCategoriesListener;
import de.handler.mobile.android.shopprototype.interfaces.OnFeaturedProductsListener;
import de.handler.mobile.android.shopprototype.models.Product;
import de.handler.mobile.android.shopprototype.ui.fragments.FeatureFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.FeatureFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductCategoryFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductCategoryFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.TitleFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.TitleFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AbstractActivity implements OnCategoriesListener, OnFeaturedProductsListener, AdapterView.OnItemSelectedListener {

    @App
    ShopApp app;

    @SystemService
    SearchManager searchManager;

    @ViewById(R.id.main_category_spinner)
    Spinner spinner;

    @ViewById(R.id.main_progress_bar)
    ProgressBar progressBar;


    @AfterInject
    public void overlayActionBar() {
        // Request Action Bar overlay before setting content view a.k.a. before @AfterViews
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }

    @AfterViews
    public void init() {
        this.setupActionBar();
        this.initTitleFragment();
        //this.getFeaturedProducts();
        this.getFakeFeaturedProducts();
        //this.getCategories();
        this.getFakeCategories();
    }


    private void initTitleFragment() {
        TitleFragment titleFragment = new TitleFragment_();
        Bundle bundle = new Bundle();
        bundle.putInt(TitleFragment.IMAGE_DRAWABLE_EXTRA, R.drawable.fairmondo);
        bundle.putString(TitleFragment.IMAGE_DESCRIPTION_STRING_EXTRA, getString(R.string.fairmondo_slogan));
        titleFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_title_container, titleFragment)
                .commit();
    }


    @Background
    public void getFeaturedProducts() {
        progressBar.setVisibility(View.VISIBLE);
        // TODO get featured products from server
    }

    /**
     * Callback for featured products server response
     */
    @Override
    public void onFeaturesProductsResponse(ArrayList<Product> products) {
        progressBar.setVisibility(View.GONE);
        this.initFeatureFragment(products);
    }

    private void getFakeFeaturedProducts() {
        ArrayList<Product> products = new ArrayList<Product>();
        for (int i = 0; i < 3; i++) {
            Product product = new Product(
                    (long) i,
                    "http://www.koellen.de/fileadmin/_migrated/pics/buecher.jpg",
                    null,
                    "Bücher",
                    null, null, null);
            products.add(i, product);
        }

        this.onFeaturesProductsResponse(products);
    }

    private void initFeatureFragment(ArrayList<Product> products) {
        FeatureFragment featureFragment = new FeatureFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FeatureFragment.FEATURED_PRODUCTS_EXTRA, products);
        featureFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_products_container, featureFragment)
                .commit();
    }


    @Background
    public void getCategories() {
        progressBar.setVisibility(View.VISIBLE);
        // TODO get categories from server
    }

    /**
     * Callback for categories server response
     */
    @Override
    public void onCategoriesResponse(ArrayList<String> categories) {
        progressBar.setVisibility(View.GONE);
        this.initSpinner(categories);
    }

    private void getFakeCategories() {
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Kassetten");
        categories.add("Hormonpräparate");
        categories.add("Sendung mit der Maus");
        categories.add("Schallplatten");
        categories.add("Elektronik");
        categories.add("Hängematten");
        categories.add("Kinderspielzeug");
        categories.add("Arzneimittel");
        categories.add("Dienstleistungen");
        categories.add("Antiqitäten & Kunst");
        categories.add("Fahrzeuge");
        categories.add("Beauty");
        categories.add("Briefmarken");
        categories.add("Bücher");
        categories.add("Schreibwaren");
        categories.add("Feinschmecker");
        categories.add("Filme");
        categories.add("Garten");
        categories.add("Haustier");
        categories.add("Schmuck");

        this.onCategoriesResponse(categories);
    }


    private void initSpinner(ArrayList<String> categories) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item,
                new ArrayList<CharSequence>());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Add basic item and categories list to the adapter
        adapter.add(getString(R.string.spinner_basic_item));
        adapter.addAll(categories);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelected(false);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * Respond to spinner actions
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Get the selected category
        if (position > 0) {
            String category = (String) parent.getItemAtPosition(position);

            ProductCategoryFragment categoryFragment = new ProductCategoryFragment_();
            Bundle bundle = new Bundle();
            bundle.putString(ProductCategoryFragment.CATEGORY_ARRAY_LIST_EXTRA, category);
            categoryFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_products_container, categoryFragment)
                    .commit();
        }
        // TODO: get products matching category from database
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * ActionBar settings
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //TODO: implement search suggestions for products --> https://developer.android.com/guide/topics/search/adding-custom-suggestions.html
        // Get the SearchView and set the searchable configuration
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, SearchableActivity_.class)));
        searchView.setIconifiedByDefault(true);

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

    }

}
