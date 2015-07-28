package de.handler.mobile.android.fairmondo.presentation.activities;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;

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
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.parceler.Parcels;

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
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.FragmentHelper;
import de.handler.mobile.android.fairmondo.presentation.controller.ProgressController;
import de.handler.mobile.android.fairmondo.presentation.controller.UIInformationController;
import de.handler.mobile.android.fairmondo.presentation.fragments.CategoryFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.CategoryFragment_;
import de.handler.mobile.android.fairmondo.presentation.fragments.FeatureFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.FeatureFragment_;
import de.handler.mobile.android.fairmondo.presentation.fragments.TitleFragment_;
import de.handler.mobile.android.fairmondo.presentation.interfaces.SharedPrefs_;


@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends AbstractActivity implements OnCategoriesListener, OnSearchResultListener, OnClickItemListener {
    private static final int AGAIN_PRESS_TIME = 1500;

    @Bean
    ProgressController mProgressController;

    @App
    FairmondoApp mApp;

    @SystemService
    SearchManager mSearchManager;

    @Bean
    RestCommunicator mRestCommunicator;

    @Pref
    SharedPrefs_ prefs;

    @ViewById(R.id.main_title_container)
    FrameLayout mTitleContainer;

    @ViewById(R.id.main_products_container)
    FrameLayout mProductsContainer;

    @ViewById(R.id.activity_main_toolbar)
    Toolbar mToolbar;

    // Time for calculating interval between last back press action and new one
    // --> exit only when pressed twice
    private long mLastBackPressTime = System.currentTimeMillis();
    private int mOrientation;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @AfterInject
    public void initRestController() {
        mRestCommunicator.setProductListener(this);
        mRestCommunicator.setCategoriesListener(this);
    }

    @AfterViews
    public void init() {
        checkNetworkState();
        setupActionBar(mToolbar);

        mOrientation = this.determineScreenOrientation();
        mApp.setCookie(prefs.cookie().get());

        this.activateTransitions();
        // Fill the title fragment with content
        this.initTitleFragment(R.drawable.ic_launcher_web, getString(R.string.fairmondo_slogan));

        // request the categories to be able to fill the category fragment with content
        this.getCategories();
    }

    private int determineScreenOrientation() {
        final Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        return display.getRotation();
    }

    private void activateTransitions() {
        // Activate standard animations for fragment containers
        final LayoutTransition titleTransition = mTitleContainer.getLayoutTransition();
        titleTransition.enableTransitionType(LayoutTransition.CHANGING);
        final LayoutTransition bodyTransition = mProductsContainer.getLayoutTransition();
        bodyTransition.enableTransitionType(LayoutTransition.CHANGING);
    }

    private void initTitleFragment(@Nullable @DrawableRes final Integer drawable, @Nullable final String text) {
        final Fragment titleFragment;
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT || mOrientation == Surface.ROTATION_0 || mOrientation == Surface.ROTATION_180) {
            if (null == drawable) {
                titleFragment = TitleFragment_.builder().mSlogan(text).build();
                FragmentHelper.replaceFragment(R.id.main_title_container, titleFragment, getSupportFragmentManager());
            } else {
                titleFragment = TitleFragment_.builder().mDrawable(drawable).mSlogan(text).build();
                FragmentHelper.replaceFragmentWithTag(R.id.main_title_container, titleFragment, getSupportFragmentManager(), "titleFragment");
            }
        } else {
            titleFragment = TitleFragment_.builder().build();
            FragmentHelper.replaceFragment(R.id.main_title_container, titleFragment, getSupportFragmentManager());
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
    public void onCategoriesResponse(@Nullable final List<FairmondoCategory> categories) {
        this.hideProgressBar();
        if (categories != null) {
            // getSubCategories is called from the CategoryFragment
            this.initStartFragment(categories);
        }
    }

    @UiThread
    public void initStartFragment(@NonNull final List<FairmondoCategory> categories) {
        if (mApp.isConnected()) {
            CategoryFragment categoryFragment = CategoryFragment_.builder().mCategoriesParcelable(Parcels.wrap(List.class, categories)).build();
            FragmentHelper.replaceFragment(R.id.main_products_container, categoryFragment, getSupportFragmentManager());
        } else {
            UIInformationController.displaySnackbarInformation(findViewById(R.id.main_products_container), getString(R.string.app_not_connected));
        }
    }

    @Override
    public void onSubCategoriesResponse(@Nullable final List<FairmondoCategory> categories) {
        this.hideProgressBar();
        if (categories != null) {
            if (categories.isEmpty() && mApp.getLastCategory() != null) {
                this.getProductSelection("", mApp.getLastCategory().getId());
            } else if (categories.size() > 0) {
                this.initCategoryFragment(categories, true);
            }
        } else {
            UIInformationController.displaySnackbarInformation(findViewById(android.R.id.content), getString(R.string.category_count_error));
        }
    }

    private void getProductSelection(@NonNull final String searchRequest, @NonNull final String categoryId) {
        if (mApp.isConnected()) {
            this.showProgressBar();
            mRestCommunicator.getProducts(searchRequest, categoryId);
        } else {
            UIInformationController.displaySnackbarInformation(findViewById(R.id.main_products_container), getString(R.string.app_not_connected));
        }
    }

    @UiThread
    public void initCategoryFragment(@NonNull final List<FairmondoCategory> categories, boolean backstack) {
        FairmondoCategory category = mApp.getLastCategory();
        if (category == null) {
            category = new FairmondoCategory();
        }
        final CategoryFragment categoryFragment = CategoryFragment_.builder().mCategoriesParcelable(Parcels.wrap(List.class, categories)).mParentCategory(category.getName()).build();
        try {
            if (backstack) {
                FragmentHelper.replaceFragmentWithTagToBackStack(R.id.main_products_container, categoryFragment, getSupportFragmentManager(), categoryFragment.getClass().getCanonicalName());
            } else {
                FragmentHelper.replaceFragment(R.id.main_products_container, categoryFragment, getSupportFragmentManager());
            }
        } catch (final IllegalStateException e) {
            Log.e(getClass().getCanonicalName(), e.getMessage());
        }
    }

    /**
     * Callback for products server response.
     */
    @Override
    public void onProductsSearchResponse(@Nullable final List<Product> products) {
        this.hideProgressBar();
        this.startSelectionActivity(products);
    }

    private void startSelectionActivity(@Nullable final List<Product> products) {
        ProductSelectionActivity_.intent(this).mProductListParcelable(Parcels.wrap(List.class, products)).start();
    }

    /**
     * Initializes the feature fragment if one is needed.
     * In case it is needed it should be called instead of initStartFragment()
     * @param urls urls which lead to featured pages.
     */
    private void initFeatureFragment(@NonNull final List<String> urls) {
        final FeatureFragment featureFragment = FeatureFragment_.builder().mUrlArrayList(Parcels.wrap(List.class, urls)).build();
        FragmentHelper.replaceFragment(R.id.main_title_container, featureFragment, getSupportFragmentManager());
    }

    /**
     * ActionBar settings.
     * Identifies the search view and sets the receiving class for the result.
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Get the SearchView and set the searchable configuration
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // change the color of the search hint text to white
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(android.R.color.white));
        searchView.setSearchableInfo(mSearchManager.getSearchableInfo(new ComponentName(this, SearchableActivity_.class)));
        searchView.setIconifiedByDefault(true);
        return true;
    }

    /**
     * Is called when the action item "show cart" is called and leads to the web cart page.
     */
    @OptionsItem(R.id.action_cart)
    void openCart() {
        final Cart cart = mApp.getCart();
        if (cart == null || cart.getCartItem() == null || cart.getCartItem().getRequestedQuantity() <= 0) {
            UIInformationController.displaySnackbarInformation(findViewById(R.id.main_products_container), getString(R.string.cart_has_no_items));
        } else {
            WebActivity_.intent(this).mUri(cart.getCartUrl()).mCookie(mApp.getCookie()).start();
        }
    }

    /**
     * Is called when the action item "delete search" is called and clears the search history.
     */
    @OptionsItem(R.id.action_search_delete)
    void clearSearchSuggestions() {
        final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
        suggestions.clearHistory();
    }

    /**
     * Informs this activity about an item click in the CategoryFragment to be able to start the progress.
     */
    @Override
    public void onItemInFragmentClicked() {
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
                setTitle(getString(R.string.app_name));
                this.initTitleFragment(R.drawable.ic_launcher_web, getString(R.string.fairmondo_slogan));
            }
        } else {
            // Handle Back Navigation - if shortly after another back is pushed exit app
            if (this.mLastBackPressTime < System.currentTimeMillis() - AGAIN_PRESS_TIME) {
                this.mLastBackPressTime = System.currentTimeMillis();
                UIInformationController.displayToastInformation(getApplicationContext(), getString(R.string.close_app));
            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        final int configOrientation = newConfig.orientation;
        if (configOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            mOrientation = Surface.ROTATION_90;
        } else {
            mOrientation = Surface.ROTATION_0;
        }
    }
}
