package de.handler.mobile.android.shopprototype.ui;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.interfaces.OnCategoriesListener;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.rest.RestController;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.ui.fragments.FeatureFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.FeatureFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.TitleFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.TitleFragment_;
import de.handler.mobile.android.shopprototype.util.Cart;

@EActivity(R.layout.activity_main)
public class MainActivity extends AbstractActivity implements OnCategoriesListener, OnSearchResultListener, AdapterView.OnItemSelectedListener {

    @App
    ShopApp app;

    @SystemService
    SearchManager searchManager;

    @Bean
    RestController restController;

    @Bean
    Cart cart;

    @ViewById(R.id.main_category_spinner)
    Spinner spinner;

    @ViewById(R.id.main_progress_bar)
    ProgressBar progressBar;

    @ViewById(R.id.main_title_container)
    LinearLayout titleContainer;


    private boolean mArticleSelection = false;


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
        // method in AbstractActivity
        this.setupActionBar();

        this.initTitleFragment();
        this.getFeaturedProducts();
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


    private void getFeaturedProducts() {
        this.changeProgressbarVisibility();
        // TODO get featured products from server
        ArrayList<Article> articles = new ArrayList<Article>(3);
        for (int i = 0; i < 3; i++) {
            Article article = new Article((long) i);
            if (i == 0) {
                article.setTitle_image_url("https://raw.githubusercontent.com/fairmondo/fairmondo/develop/app/assets/images/welcome/billboard_books_big.jpg");
                article.setTitle("Bücher, Bücher, Bücher!");
            } else if (i == 1) {
                article.setTitle_image_url("https://raw.githubusercontent.com/fairmondo/fairmondo/develop/app/assets/images/welcome/billboard_coffee_big.jpg");
                article.setTitle("Gutes Genießen.");
            } else {
                article.setTitle_image_url("https://raw.githubusercontent.com/fairmondo/fairmondo/develop/app/assets/images/welcome/billboard_discover_big.jpg");
                article.setTitle("Gutes einfach entdecken");
            }
            articles.add(article);
        }
        this.initFeatureFragment(articles);
    }

    private void getProductSelection(String searchRequest, int categoryId) {
        if (app.isConnected()) {
            this.changeProgressbarVisibility();
            restController.getProduct(searchRequest, categoryId);
        } else {
            Toast.makeText(this, getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback for featured products server response
     */
    @Override
    public void onProductsSearchResponse(ArrayList<Article> products) {
        this.changeProgressbarVisibility();
        if (mArticleSelection) {
            this.initSelectionFragment(products);
        } else {
            this.initFeatureFragment(products);
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

    private void initSelectionFragment(ArrayList<Article> products) {
        ProductSelectionFragment selectionFragment = new ProductSelectionFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ProductSelectionFragment.SELECTION_ARRAY_LIST_EXTRA, products);
        selectionFragment.setArguments(bundle);

        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_products_container, selectionFragment)
                    .commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }





    private void getCategories() {
        this.changeProgressbarVisibility();
        // TODO get categories from server
    }

    /**
     * Callback for categories server response
     */
    @Override
    public void onCategoriesResponse(ArrayList<String> categories) {
        this.changeProgressbarVisibility();
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
        // Get the selected category and the products matching the category
        if (position > 0) {
            //String category = (String) parent.getItemAtPosition(position);
            this.getProductSelection("", position);
            mArticleSelection = true;
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
            case R.id.action_cart:
                this.openCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCart() {
        if (cart.getArticles().size() > 0) {
            startActivity(new Intent(this, CartActivity_.class));
        } else {
            Toast.makeText(this, getString(R.string.cart_has_no_items), Toast.LENGTH_SHORT).show();
        }
    }


    private void openSettings() {

    }

    @UiThread
    public void changeProgressbarVisibility() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            //titleContainer.setVisibility(View.INVISIBLE);

        } else {
            progressBar.setVisibility(View.INVISIBLE);
            //titleContainer.setVisibility(View.VISIBLE);
        }
    }

}
