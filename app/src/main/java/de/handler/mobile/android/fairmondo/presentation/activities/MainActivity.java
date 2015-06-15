package de.handler.mobile.android.fairmondo.presentation.activities;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.ComponentName;
import android.os.Build;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.Cart;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.data.datasource.SearchSuggestionProvider;
import de.handler.mobile.android.fairmondo.data.interfaces.OnCategoriesListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnClickItemListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.FragmentHelper;
import de.handler.mobile.android.fairmondo.presentation.controller.ProgressController;
import de.handler.mobile.android.fairmondo.presentation.fragments.CategoryFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.CategoryFragment_;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment_;
import de.handler.mobile.android.fairmondo.presentation.fragments.TitleFragment_;
import de.handler.mobile.android.fairmondo.presentation.fragments.WebFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.WebFragment_;


@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends AbstractActivity implements OnCategoriesListener, OnDetailedProductListener, OnSearchResultListener, OnClickItemListener {
    private static final int AGAIN_PRESS_TIME = 1500;
    private static final int PROGRESS_BAR_CONTAINER = R.id.main_products_container;

    @Bean
    ProgressController mProgressController;

    @App
    FairmondoApp app;

    @SystemService
    SearchManager searchManager;

    @Bean
    RestCommunicator restController;

    @ViewById(R.id.main_title_container)
    FrameLayout titleContainer;

    @ViewById(R.id.main_products_container)
    FrameLayout productsContainer;

    @ViewById(R.id.activity_main_toolbar)
    Toolbar toolbar;

    // private ProgressController mProgressController;
    // Time for calculating interval between last back press action and new one
    // --> exit only when pressed twice
    private long mLastBackPressTime = System.currentTimeMillis();
    private int mProductsCount = 0;
    private List<Product> mProducts;


    @AfterInject
    public void initRestController() {
        restController.setProductListener(this);
        restController.setCategoriesListener(this);
        restController.setDetailedProductListener(this);
    }

    @AfterViews
    public void init() {
        this.initToolbar();
        this.activateTransitions();
        this.initTitleFragment(R.drawable.ic_launcher_web, getString(R.string.fairmondo_slogan));
        this.getCategories();
    }


    private void initToolbar() {
        ActionBar actionBar = this.setupActionBar(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    private void activateTransitions() {
        // Activate standard animations for fragment containers
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            final LayoutTransition titleTransition = titleContainer.getLayoutTransition();
            titleTransition.enableTransitionType(LayoutTransition.CHANGING);
            final LayoutTransition bodyTransition = productsContainer.getLayoutTransition();
            bodyTransition.enableTransitionType(LayoutTransition.CHANGING);
        }
    }

    private void initTitleFragment(@DrawableRes final Integer drawable, final String text) {
        Fragment titleFragment;
        if (drawable == null) {
            titleFragment = TitleFragment_.builder().mSlogan(text).build();
            FragmentHelper.replaceFragment(R.id.main_title_container, titleFragment, getSupportFragmentManager());
        } else {
            titleFragment = TitleFragment_.builder().mDrawable(drawable).mSlogan(text).build();
            FragmentHelper.replaceFragmentWithTag(R.id.main_title_container, titleFragment, getSupportFragmentManager(), "titleFragment");
        }
    }

    private void getCategories() {
        this.showProgressBar();
        restController.getCategories();
    }

    /**
     * Callback for categories server response.
     * @param categories a list of available categories
     */
    @Override
    public void onCategoriesResponse(final List<FairmondoCategory> categories) {
        this.hideProgressBar();
        if (categories != null) {
            // getSubCategories is called from the CategoryFragment
            this.initStartFragment(categories);
        }
    }

    @UiThread
    public void initStartFragment(List<FairmondoCategory> categories) {
        if (app.isConnected()) {
            CategoryFragment categoryFragment = CategoryFragment_.builder().mCategoriesParcelable(Parcels.wrap(List.class, categories)).build();
            FragmentHelper.replaceFragment(R.id.main_products_container, categoryFragment, getSupportFragmentManager());
        } else {
            Toast.makeText(getApplicationContext(), R.string.app_not_connected, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSubCategoriesResponse(final List<FairmondoCategory> categories) {
        this.hideProgressBar();
        if (categories != null) {
            if (categories.isEmpty()) {
                this.getProductSelection("", app.getLastCategory().getId());
            } else {
                this.initCategoryFragment(categories, true);
            }
        }
    }

    @UiThread
    public void initCategoryFragment(final List<FairmondoCategory> categories, boolean backstack) {
        CategoryFragment categoryFragment = CategoryFragment_.builder().mCategoriesParcelable(Parcels.wrap(List.class, categories)).build();
        try {
            if (backstack) {
                FragmentHelper.replaceFragmentWithTagToBackStack(R.id.main_products_container, categoryFragment, getSupportFragmentManager(), "categoryFragment");
            } else {
                FragmentHelper.replaceFragment(R.id.main_products_container, categoryFragment, getSupportFragmentManager());
            }
        } catch (IllegalStateException e) {
            // TODO send exception to fairmondo server
            e.printStackTrace();
        }
    }

    private void getProductSelection(final String searchRequest, final String categoryId) {
        if (app.isConnected()) {
            this.showProgressBar();
            restController.getProducts(searchRequest, categoryId);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback for products server response.
     */
    @Override
    public void onProductsSearchResponse(final List<Product> products) {
        if (products != null && products.size() > 0) {
            this.getDetailedProducts(products);
        } else {
            this.hideProgressBar();
            this.initSelectionFragment(null);
        }
    }

    // The basic product information has been received,
    // now query more detailed information about each product
    private void getDetailedProducts(final List<Product> products) {
        // Set product count as listener responds to each single product
        // and app needs to react when all products have finished loading
        mProductsCount = products.size();
        mProducts = new ArrayList<>(products.size());

        for (Product product : products) {
            restController.getDetailedProduct(product.getSlug());
        }
    }

    @Override
    public void onDetailedProductResponse(final Product product) {
        mProducts.add(product);
        if (mProducts.size() == mProductsCount) {
            this.hideProgressBar();
            this.initSelectionFragment(mProducts);
        }
    }

    private void initSelectionFragment(final List<Product> products) {
        ProductSelectionFragment selectionFragment = new ProductSelectionFragment_();
        if (products != null) {
            selectionFragment = ProductSelectionFragment_.builder().mProductsParcelable(Parcels.wrap(List.class, products)).build();
        }

        try {
            FragmentHelper.replaceFragmentWithTagToBackStack(R.id.main_products_container, selectionFragment, getSupportFragmentManager(), "selectionFragment");
        } catch (IllegalStateException e) {
            // TODO send exception to fairmondo server
            e.printStackTrace();
        }
    }

    /**
     * Initializes the feature fragment if one is needed.
     * In case it is needed it should be called instead of initStartFragment()
     * @param featuredProductsHtml a html string of the featured products of fairmondo.
     */
    private void initFeatureFragment(final String featuredProductsHtml) {
        WebFragment featureFragment = WebFragment_.builder().mHtml(featuredProductsHtml).build();
        FragmentHelper.replaceFragment(R.id.main_products_container, featureFragment, getSupportFragmentManager());
    }

    /**
     * ActionBar settings.
     * Identifies the search view and sets the receiving class for the result.
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Get the SearchView and set the searchable configuration
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchableActivity_.class)));
        searchView.setIconifiedByDefault(true);
        return true;
    }

    /**
     * Is called when the action item "show cart" is called and leads to the web cart page.
     */
    @OptionsItem(R.id.action_cart)
    void openCart() {
        Cart cart = app.getCart();
        if (cart == null || cart.getCartItem() == null || cart.getCartItem().getRequestedQuantity() <= 0) {
            Toast.makeText(this, getString(R.string.cart_has_no_items), Toast.LENGTH_SHORT).show();
        } else {
            WebActivity_.intent(this).mUri(cart.getCartUrl()).mCookie(app.getCookie()).start();
        }
    }

    /**
     * Is called when the action item "settings" is called and shows the preferences screen.
     */
    @OptionsItem(R.id.action_settings)
    void openSettings() {
        // TODO: to implement
    }

    /**
     * Is called when the action item "delete search" is called and clears the search history.
     */
    @OptionsItem(R.id.action_search_delete)
    void clearSearchSuggestions() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
        suggestions.clearHistory();
    }

    /**
     * Informs this activity about an item click in the CategoryFragment to be able to start the progress.
     */
    @Override
    public void onItemClick() {
        this.showProgressBar();
        this.initTitleFragment(null, null);
    }

    /**
     * Start the progress indication by overlaying the current screen with the ProgressFragment.
     */
    private void showProgressBar() {
        mProgressController.startProgress(getSupportFragmentManager(), PROGRESS_BAR_CONTAINER);
    }

    /**
     * Stop the progress indication by removing the current screen overlay.
     */
    private void hideProgressBar() {
        mProgressController.stopProgress();
    }

    /**
     * {@inheritDoc}
     *
     * Override onBackPressed for being able to work with fragments.
     * Also inform the user of the moment the app closes
     */
    @Override
    public void onBackPressed() {
        this.hideProgressBar();
        app.setLastCategory(null);
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();

            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                // Reset standard title
                this.initTitleFragment(R.drawable.ic_launcher_web, getString(R.string.fairmondo_slogan));
            }
        } else {
            // Reset standard title
            this.initTitleFragment(R.drawable.ic_launcher_web, getString(R.string.fairmondo_slogan));
            // Handle Back Navigation - if shortly after another back is pushed exit app
            if (this.mLastBackPressTime < System.currentTimeMillis() - AGAIN_PRESS_TIME) {
                this.mLastBackPressTime = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), getString(R.string.close_app), Toast.LENGTH_SHORT).show();

            } else {
                super.onBackPressed();
            }
        }
    }
}
