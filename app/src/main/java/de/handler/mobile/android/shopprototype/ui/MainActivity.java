package de.handler.mobile.android.shopprototype.ui;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.datasource.DatabaseController;
import de.handler.mobile.android.shopprototype.datasource.database.Category;
import de.handler.mobile.android.shopprototype.interfaces.OnCategoriesListener;
import de.handler.mobile.android.shopprototype.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.rest.RestController;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.rest.json.model.Cart;
import de.handler.mobile.android.shopprototype.ui.fragments.CategoryFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.CategoryFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.FeatureFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.FeatureFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.TitleFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.TitleFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.WebFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.WebFragment_;


@EActivity(R.layout.activity_main)
public class MainActivity extends AbstractActivity implements OnCategoriesListener,
        OnDetailedProductListener, OnSearchResultListener, AdapterView.OnItemSelectedListener {

    private static final long AGAIN_PRESS_TIME = 1500;

    @App
    ShopApp app;

    @SystemService
    SearchManager searchManager;

    @Bean
    RestController restController;

    @Bean
    DatabaseController databaseController;


    @ViewById(R.id.main_category_spinner)
    Spinner spinner;

    @ViewById(R.id.main_progress_bar)
    ProgressBar progressBar;

    @ViewById(R.id.main_title_container)
    LinearLayout titleContainer;


    private boolean mSpinnerSelection = false;
    private ArrayList<Category> mCategories;

    // Time for calculating interval between last back press action and new one
    // --> exit only when pressed twice
    private long mLastBackPressTime = System.currentTimeMillis();
    private int mProductsCount = 0;
    private ArrayList<Article> mProducts;


    @AfterInject
    public void initRestController() {
        restController.setProductListener(this);
        restController.setCategoriesListener(this);
        restController.setDetailedProductListener(this);
    }


    @AfterViews
    public void init() {
        // method in AbstractActivity
        this.setupActionBar();
        this.checkNetworkState();

        this.initTitleFragment();
        this.initStartFragment();
        this.getCategories();

        List<Category> categoryList = databaseController.getCategories();
        if (categoryList != null) {
            this.initSpinner(new ArrayList<Category>(categoryList));
        }
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


    private void initStartFragment() {
        this.showProgressbar();

        Bundle bundle = new Bundle();
        bundle.putString(WebFragment.URI, "http://mitmachen.fairmondo.de/anteile-zeichnen/");

        WebFragment webFragment = new WebFragment_();
        webFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_products_container, webFragment)
                .commit();

        this.hideProgressbar();
    }





    private void getCategories() {
        restController.getCategories();
    }


    /**
     * Callback for categories server response
     * @param categories a list of available categories
     */
    @Override
    public void onCategoriesResponse(ArrayList<Category> categories) {
        mCategories = categories;
        databaseController.setCategories(mCategories);
        databaseController.setSearchSuggestions(mCategories);

        //this.hideProgressbar();
        this.initSpinner(categories);
    }


    @UiThread
    public void initSpinner(ArrayList<Category> categories) {
        // Get category strings
        ArrayList<String> categoryStrings = new ArrayList<String>(categories.size());
        for (Category category : categories) {
            categoryStrings.add(category.getName());
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item,
                new ArrayList<CharSequence>());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Add basic item and categories list to the adapter
        adapter.add(getString(R.string.spinner_basic_item));
        adapter.addAll(categoryStrings);
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
        // Get the selected category and the products matching the category
        if (position > 0) {
            //String category = (String) parent.getItemAtPosition(position);
            this.getSubCategories(mCategories.get(position-1).getId().intValue());
            mSpinnerSelection = true;
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    private void getSubCategories(int id) {
        this.showProgressbar();
        restController.getSubCategories(id);
    }


    @Override
    public void onSubCategoriesResponse(ArrayList<Category> categories) {

        if (categories.size() >= 1) {
            this.hideProgressbar();
            this.initCategoryFragment(categories);
        } else {
            this.getProductSelection("", app.getLastCategory());
        }
    }


    @UiThread
    public void initCategoryFragment(ArrayList<Category> categories) {
        CategoryFragment categoryFragment = new CategoryFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CategoryFragment.CATEGORIES_ARRAY_LIST_EXTRA, categories);
        categoryFragment.setArguments(bundle);

        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_products_container, categoryFragment)
                    .addToBackStack("categoryFragment")
                    .commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }



    private void getProductSelection(String searchRequest, int categoryId) {
        if (app.isConnected()) {
            this.showProgressbar();
            restController.getProduct(searchRequest, categoryId);
        } else {
            this.hideProgressbar();
            Toast.makeText(this, getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback for featured products server response
     */
    @Override
    public void onProductsSearchResponse(ArrayList<Article> products) {
        if (mSpinnerSelection) {
            if (products != null && products.size() > 0) {
                this.getDetailedProducts(products);
            } else {
                this.hideProgressbar();
                this.initSelectionFragment(null);
            }
        } else {
            this.hideProgressbar();
            this.initFeatureFragment(products);
        }
    }



    private void getDetailedProducts(ArrayList<Article> products) {
        // Set products count as listener responds to every product
        // and app needs to react when all products have finished loading
        mProductsCount = products.size();

        mProducts = new ArrayList<Article>(products.size());
        for (Article product : products) {
            restController.getDetailedProduct(product.getSlug());
        }
    }

    @Override
    public void onDetailedProductResponse(Article article) {
        mProducts.add(article);

        if (mProducts.size() == mProductsCount) {
            this.hideProgressbar();
            this.initSelectionFragment(mProducts);
        }
    }




    private void initSelectionFragment(ArrayList<Article> products) {
        ProductSelectionFragment selectionFragment = new ProductSelectionFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ProductSelectionFragment.SELECTION_ARRAY_LIST_EXTRA, products);
        selectionFragment.setArguments(bundle);

        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_products_container, selectionFragment)
                    .addToBackStack("selectionFragment")
                    .commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void initFeatureFragment(ArrayList<Article> products) {
        FeatureFragment featureFragment = new FeatureFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FeatureFragment.FEATURED_PRODUCTS_EXTRA, products);
        featureFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_products_container, featureFragment)
                .commit();
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
            case R.id.action_cart:
                this.openCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCart() {
        Cart cart = app.getCart();
        if (cart != null &&
                cart.getLine_item() != null &&
                cart.getLine_item().getRequested_quantity() > 0) {

            Intent intent = new Intent(this, WebActivity_.class);
            intent.putExtra(WebFragment.URI, cart.getCart_url());
            intent.putExtra(WebFragment.COOKIE, app.getCookie());
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.cart_has_no_items), Toast.LENGTH_SHORT).show();
        }
    }


    private void openSettings() {

    }


    @UiThread
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
        titleContainer.setVisibility(View.INVISIBLE);
    }

    @UiThread
    public void hideProgressbar() {
        progressBar.setVisibility(View.INVISIBLE);
        titleContainer.setVisibility(View.VISIBLE);
    }

    // Override onBackPressed for being able to work with fragments.
    // Also inform the user of the moment the app closes
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
        } else {
            if (this.mLastBackPressTime < System.currentTimeMillis() - AGAIN_PRESS_TIME) {
                this.mLastBackPressTime = System.currentTimeMillis();
                Toast.makeText(this, getString(R.string.close_app), Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
