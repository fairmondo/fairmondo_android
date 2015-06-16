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
import de.handler.mobile.android.fairmondo.presentation.controller.ErrorController;
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

    @Bean
    ProgressController mProgressController;

    @App
    FairmondoApp mApp;

    @SystemService
    SearchManager mSearchManager;

    @Bean
    RestCommunicator mRestCommunicator;

    @ViewById(R.id.main_title_container)
    FrameLayout mTitleContainer;

    @ViewById(R.id.main_products_container)
    FrameLayout mProductsContainer;

    @ViewById(R.id.activity_main_toolbar)
    Toolbar mToolbar;

    // private ProgressController mProgressController;
    // Time for calculating interval between last back press action and new one
    // --> exit only when pressed twice
    private long mLastBackPressTime = System.currentTimeMillis();
    private int mProductsCount;
    private List<Product> mProducts;

    @Override
    protected void onResume() {
        super.onResume();
        mProductsCount = 0;
    }

    @AfterInject
    public void initRestController() {
        mRestCommunicator.setProductListener(this);
        mRestCommunicator.setCategoriesListener(this);
        mRestCommunicator.setDetailedProductListener(this);
    }

    @AfterViews
    public void init() {
        this.initToolbar();
        this.activateTransitions();
        // Fill the title fragment with content
        this.initTitleFragment(R.drawable.ic_launcher_web, getString(R.string.fairmondo_slogan));
        // request the categories to be able to fill the category fragment with content
        this.getCategories();
    }


    private void initToolbar() {
        ActionBar actionBar = setupActionBar(mToolbar);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    private void activateTransitions() {
        // Activate standard animations for fragment containers
        final LayoutTransition titleTransition = mTitleContainer.getLayoutTransition();
        titleTransition.enableTransitionType(LayoutTransition.CHANGING);
        final LayoutTransition bodyTransition = mProductsContainer.getLayoutTransition();
        bodyTransition.enableTransitionType(LayoutTransition.CHANGING);
    }

    private void initTitleFragment(@DrawableRes final Integer drawable, final String text) {
        Fragment titleFragment;
        if (null == drawable) {
            titleFragment = TitleFragment_.builder().mSlogan(text).build();
            FragmentHelper.replaceFragment(R.id.main_title_container, titleFragment, getSupportFragmentManager());
        } else {
            titleFragment = TitleFragment_.builder().mDrawable(drawable).mSlogan(text).build();
            FragmentHelper.replaceFragmentWithTag(R.id.main_title_container, titleFragment, getSupportFragmentManager(), "titleFragment");
        }
    }

    private void getCategories() {
        this.showProgressBar();
        mRestCommunicator.getCategories();
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
        if (mApp.isConnected()) {
            CategoryFragment categoryFragment = CategoryFragment_.builder().mCategoriesParcelable(Parcels.wrap(List.class, categories)).build();
            FragmentHelper.replaceFragment(R.id.main_products_container, categoryFragment, getSupportFragmentManager());
        } else {
            ErrorController.displayErrorToast(getApplicationContext(), getString(R.string.app_not_connected));
        }
    }

    @Override
    public void onSubCategoriesResponse(final List<FairmondoCategory> categories) {
        this.hideProgressBar();
        if (categories != null) {
            if (categories.isEmpty() && mApp.getLastCategory() != null) {
                this.getProductSelection("", mApp.getLastCategory().getId());
            } else if (categories.size() > 0) {
                this.initCategoryFragment(categories, true);
            }
        }
    }

    @UiThread
    public void initCategoryFragment(final List<FairmondoCategory> categories, boolean backstack) {
        CategoryFragment categoryFragment = CategoryFragment_.builder().mCategoriesParcelable(Parcels.wrap(List.class, categories)).build();
        try {
            if (backstack) {
                FragmentHelper.replaceFragmentWithTagToBackStack(R.id.main_products_container, categoryFragment, getSupportFragmentManager(), categoryFragment.getClass().getCanonicalName());
            } else {
                FragmentHelper.replaceFragment(R.id.main_products_container, categoryFragment, getSupportFragmentManager());
            }
        } catch (IllegalStateException e) {
            // TODO send exception to fairmondo server
            ErrorController.displayErrorSnackbar(findViewById(android.R.id.content), e.getMessage());
        }
    }

    private void getProductSelection(final String searchRequest, final String categoryId) {
        if (mApp.isConnected()) {
            this.showProgressBar();
            mRestCommunicator.getProducts(searchRequest, categoryId);
        } else {
            ErrorController.displayErrorToast(getApplicationContext(), getString(R.string.app_not_connected));
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
            mRestCommunicator.getDetailedProduct(product.getSlug());
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
            ErrorController.displayErrorSnackbar(findViewById(android.R.id.content), e.getMessage());
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
        searchView.setSearchableInfo(mSearchManager.getSearchableInfo(new ComponentName(this, SearchableActivity_.class)));
        searchView.setIconifiedByDefault(true);
        return true;
    }

    /**
     * Is called when the action item "show cart" is called and leads to the web cart page.
     */
    @OptionsItem(R.id.action_cart)
    void openCart() {
        Cart cart = mApp.getCart();
        if (cart == null || cart.getCartItem() == null || cart.getCartItem().getRequestedQuantity() <= 0) {
            Toast.makeText(this, getString(R.string.cart_has_no_items), Toast.LENGTH_SHORT).show();
        } else {
            WebActivity_.intent(this).mUri(cart.getCartUrl()).mCookie(mApp.getCookie()).start();
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
        mProgressController.startProgress(getSupportFragmentManager(), android.R.id.content);
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
        mApp.setLastCategory(null);
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
