package de.handler.mobile.android.fairmondo.ui.activities;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
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

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datasource.DatabaseController;
import de.handler.mobile.android.fairmondo.datasource.database.Category;
import de.handler.mobile.android.fairmondo.datasource.database.SearchSuggestion;
import de.handler.mobile.android.fairmondo.interfaces.OnCategoriesListener;
import de.handler.mobile.android.fairmondo.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.fairmondo.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.rest.RestController;
import de.handler.mobile.android.fairmondo.rest.json.Article;
import de.handler.mobile.android.fairmondo.rest.json.model.Cart;
import de.handler.mobile.android.fairmondo.ui.fragments.CategoryFragment;
import de.handler.mobile.android.fairmondo.ui.fragments.CategoryFragment_;
import de.handler.mobile.android.fairmondo.ui.fragments.FeatureFragment;
import de.handler.mobile.android.fairmondo.ui.fragments.FeatureFragment_;
import de.handler.mobile.android.fairmondo.ui.fragments.ProductSelectionFragment;
import de.handler.mobile.android.fairmondo.ui.fragments.ProductSelectionFragment_;
import de.handler.mobile.android.fairmondo.ui.fragments.TitleFragment;
import de.handler.mobile.android.fairmondo.ui.fragments.TitleFragment_;
import de.handler.mobile.android.fairmondo.ui.fragments.WebFragment;
import de.handler.mobile.android.fairmondo.ui.fragments.WebFragment_;


@EActivity(R.layout.activity_main)
public class MainActivity extends AbstractActivity implements OnCategoriesListener,
        OnDetailedProductListener, OnSearchResultListener, AdapterView.OnItemSelectedListener {

    private static final long AGAIN_PRESS_TIME = 1500;

    @App
    FairmondoApp app;

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

    @ViewById(R.id.main_products_container)
    LinearLayout productsContainer;


    private boolean mSpinnerSelection = false;
    private ArrayList<Category> mCategories;

    // Time for calculating interval between last back press action and new one
    // --> exit only when pressed twice
    private long mLastBackPressTime = System.currentTimeMillis();
    private int mProductsCount = 0;
    private ArrayList<Article> mProducts;
    private String mCurrentCategoryString;


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

        // Activate standard animations for fragment containers
        if (Build.VERSION.SDK_INT > 15) {
            LayoutTransition transition1 = productsContainer.getLayoutTransition();
            transition1.enableTransitionType(LayoutTransition.CHANGING);

            LayoutTransition transition2 = titleContainer.getLayoutTransition();
            transition2.enableTransitionType(LayoutTransition.CHANGING);
        }

        this.initTitleFragment(R.drawable.fairmondo_small, getString(R.string.fairmondo_slogan));
        this.initStartFragment();
        this.getCategories();

        /*List<Category> categoryList = databaseController.getCategories();
        if (categoryList != null) {
            this.initSpinner(new ArrayList<Category>(categoryList));
        }*/
    }


    private void initTitleFragment(Integer drawable, String text) {
        TitleFragment titleFragment = new TitleFragment_();
        Bundle bundle = new Bundle();
        if (drawable != null) {
            bundle.putInt(TitleFragment.IMAGE_DRAWABLE_EXTRA, drawable);
        }
        bundle.putString(TitleFragment.IMAGE_DESCRIPTION_STRING_EXTRA, text);
        titleFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_title_container, titleFragment)
                .commit();
    }


    private void initStartFragment() {
        if (app.isConnected()) {

            Bundle bundle = new Bundle();
            bundle.putString(WebFragment.URI, "http://mitmachen.fairmondo.de/anteile-zeichnen/");

            WebFragment webFragment = new WebFragment_();
            webFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_products_container, webFragment)
                    .commit();

        } else {
            Toast.makeText(getApplicationContext(), R.string.app_not_connected, Toast.LENGTH_SHORT).show();
        }
    }





    private void getCategories() {
        this.showProgressbar();
        progressBar.setMax(2);
        restController.getCategories();
    }


    /**
     * Callback for categories server response
     * @param categories a list of available categories
     */
    @Override
    public void onCategoriesResponse(ArrayList<Category> categories) {
        progressBar.setProgress(progressBar.getProgress()+1);
        this.hideProgressbar();

        if (categories != null) {
            mCategories = categories;
            databaseController.setCategories(categories);

            this.initSpinner(categories);
        }
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
        // Clear Fragment stack as a new search begins and back navigation should only
        // navigate back during one search
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStackImmediate();
        }

        // Get the selected category and the products matching the category
        mSpinnerSelection = true;
        if (position > 0) {
            mCurrentCategoryString = (String) parent.getItemAtPosition(position);
            app.setLastCategory(mCategories.get(position - 1));
            this.getSubCategories(mCategories.get(position-1).getId().intValue());
            this.initTitleFragment(null, mCurrentCategoryString);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    private void getSubCategories(int id) {
        restController.getSubCategories(id);
    }


    @Override
    public void onSubCategoriesResponse(ArrayList<Category> categories) {
        if (categories != null) {
            if (categories.size() >= 1) {
                Category category = new Category(-1L);
                category.setName(getString(R.string.all_products));
                categories.add(0, category);
                this.initCategoryFragment(categories);
                this.initTitleFragment(null, app.getLastCategory().getName());
            } else {
                this.initTitleFragment(null, app.getLastCategory().getName());
                this.getProductSelection("", app.getLastCategory().getId().intValue());
            }
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
            Toast.makeText(getApplicationContext(), getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback for featured products server response
     */
    @Override
    public void onProductsSearchResponse(ArrayList<Article> products) {
        progressBar.setProgress(progressBar.getProgress()+1);

        if (mSpinnerSelection) {
            if (products != null && products.size() > 0) {
                progressBar.setMax(progressBar.getProgress()+products.size());

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

        // Set maximal progress
        progressBar.setProgress(1);
        progressBar.setMax(products.size()+1);

        for (Article product : products) {
            restController.getDetailedProduct(product.getSlug());
        }
    }

    @Override
    public void onDetailedProductResponse(Article article) {
        mProducts.add(article);

        // Increment progress
        progressBar.setProgress(progressBar.getProgress()+1);

        if (mProducts.size() == mProductsCount) {
            this.hideProgressbar();
            this.initSelectionFragment(mProducts);

            databaseController.setSearchSuggestions(mProducts, mCurrentCategoryString);
            List<SearchSuggestion> searchSuggestions = databaseController.getSearchSuggestions();
            Log.d("MAIN_ACTIVITY", "products with category " + mCurrentCategoryString + " added to searchSuggestionTable" + " \n with old size " + searchSuggestions.size());
        }
    }




    private void initSelectionFragment(ArrayList<Article> products) {
        ProductSelectionFragment selectionFragment = new ProductSelectionFragment_();

        if (products != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(ProductSelectionFragment.SELECTION_ARRAY_LIST_EXTRA, products);
            selectionFragment.setArguments(bundle);
        }

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


    @Override
    public void showProgressBar() {
        this.showProgressbar();
    }

    @Override
    public void hideProgressBar() {
        this.hideProgressbar();
    }

    @UiThread
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(1);
        //titleContainer.setVisibility(View.INVISIBLE);
    }

    @UiThread
    public void hideProgressbar() {
        progressBar.setVisibility(View.INVISIBLE);
        //titleContainer.setVisibility(View.VISIBLE);
    }

    // Override onBackPressed for being able to work with fragments.
    // Also inform the user of the moment the app closes
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            this.initTitleFragment(null, getString(R.string.fairmondo_slogan));

            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                // Reset standard title
                this.initTitleFragment(R.drawable.fairmondo_small, getString(R.string.fairmondo_slogan));
                spinner.setSelection(0);
            }
        } else {
            // Handle Back Navigation - if shortly after another back is pushed exit app
            if (this.mLastBackPressTime < System.currentTimeMillis() - AGAIN_PRESS_TIME) {
                this.mLastBackPressTime = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), getString(R.string.close_app), Toast.LENGTH_SHORT).show();

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
