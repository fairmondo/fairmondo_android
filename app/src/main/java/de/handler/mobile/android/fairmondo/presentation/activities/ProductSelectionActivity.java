package de.handler.mobile.android.fairmondo.presentation.activities;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.Cart;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.FragmentHelper;
import de.handler.mobile.android.fairmondo.presentation.controller.UIInformationController;
import de.handler.mobile.android.fairmondo.presentation.fragments.FilterFragment_;
import de.handler.mobile.android.fairmondo.presentation.fragments.SortFragment_;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment_;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnEndlessScrollListener;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnFilterSelectedListener;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnSortingSelectedListener;

/**
 * An Activity displaying products.
 * Either all or a selection.
 */
@EActivity(R.layout.activity_product_selection)
@OptionsMenu(R.menu.product_selection)
public class ProductSelectionActivity extends AbstractActivity implements OnSortingSelectedListener, OnFilterSelectedListener, OnEndlessScrollListener {
    private static final String TAG_PRODUCT_SELECTION = "product_selection_fragment";

    @Extra
    Parcelable mProductListParcelable;

    @App
    FairmondoApp mApp;

    @ViewById(R.id.activity_product_selection_toolbar)
    Toolbar mToolbar;

    @Bean
    RestCommunicator mRestCommunicator;

    private Fragment mSortFragment;
    private Fragment mFilterFragment;
    private List<Product> mProducts;

    private boolean mFilterFairSelected;
    private boolean mFilterEcologicalSelected;
    private boolean mSortPriceSelected;
    private boolean mSortAlphabeticalSelected;
    private boolean mSortConditionSelected;

    @AfterViews
    void init() {
        setHomeUpEnabled(mToolbar);
        mRestCommunicator.setProductListener(new OnSearchResultListener() {
            @Override
            public void onProductsSearchResponse(List<Product> products) {
                if (products == null) {
                    UIInformationController.displaySnackbarInformation(findViewById(android.R.id.content), getString(R.string.end_of_list));
                    return;
                }

                final boolean updated = mProducts.addAll(products);
                if (updated) {
                    updateSelectionFragment(mProducts);
                }
            }
        });

        mSortFragment = SortFragment_.builder().build();
        mFilterFragment = FilterFragment_.builder().build();
        mProducts = Parcels.unwrap(mProductListParcelable);
        this.startProductSelectionFragment(mProducts);
    }

    private void updateSelectionFragment(@NonNull final List<Product> products) {
        ProductSelectionFragment fragment = (ProductSelectionFragment) getSupportFragmentManager().findFragmentByTag(TAG_PRODUCT_SELECTION);
        List<Product> filteredProducts = products;
        if (null != fragment) {
            if (mFilterFairSelected) {
                filteredProducts = this.filterFairProducts(products);
            }
            if (mFilterEcologicalSelected) {
                filteredProducts = this.filterEcologicalProducts(products);
            }
            if (mSortConditionSelected) {
                filteredProducts = this.sortProductsForCondition(products);
            }
            if (mSortAlphabeticalSelected) {
                filteredProducts = this.sortProductsAlphabetically(products);
            }
            if (mSortPriceSelected) {
                filteredProducts = this.sortProductsForPrice(products);
            }
            fragment.updateAdapter(filteredProducts);
        }
    }

    /**
     * Is called when the action item "show cart" is called and leads to the web cart page.
     */
    @OptionsItem(R.id.action_cart)
    void openCart() {
        final Cart cart = mApp.getCart();
        if (cart == null || cart.getCartItem() == null || cart.getCartItem().getRequestedQuantity() <= 0) {
            UIInformationController.displaySnackbarInformation(findViewById(R.id.activity_product_selection_main_container), getString(R.string.cart_has_no_items));
        } else {
            WebActivity_.intent(this).mUri(cart.getCartUrl()).mCookie(mApp.getCookie()).start();
        }
    }

    /**
     * Is called when the action item "filter is called and leads to filter fragment.
     */
    @OptionsItem(R.id.action_filter)
    void filterProducts() {
        FragmentHelper.replaceFragment(android.R.id.content, mFilterFragment, getSupportFragmentManager());
    }

    @Override
    public void onFairFilterSelected(final boolean selected) {
        final List<Product> products;
        if (selected) {
            products = this.filterFairProducts(mProducts);
        } else {
            products = mProducts;
        }
        this.startProductSelectionFragment(products);
        mFilterFairSelected = selected;
    }

    @Override
    public void onEcologicalFilterSelected(final boolean selected) {
        final List<Product> products;
        if (selected) {
            products = this.filterEcologicalProducts(mProducts);
        } else {
            products = mProducts;
        }
        this.startProductSelectionFragment(products);
        mFilterEcologicalSelected = selected;
    }

    @Override
    public void onFilterFinish() {
        FragmentHelper.removeFragment(mFilterFragment, getSupportFragmentManager());
    }

    @NonNull
    private List<Product> filterFairProducts(@NonNull final List<Product> products) {
        final List<Product> filteredProducts = new ArrayList<>();
        for (final Product product : products) {
            if (null != product.getTags() && product.getTags().isFair()) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    @NonNull
    private List<Product> filterEcologicalProducts(@NonNull final List<Product> products) {
        final List<Product> filteredProducts = new ArrayList<>();
        for (final Product product : products) {
            if (null != product.getTags() && product.getTags().isEcologic()) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    /**
     * Is called when the action item "sort" is called and leads to the sort fragment.
     */
    @OptionsItem(R.id.action_sort)
    void sortProducts() {
        FragmentHelper.replaceFragment(android.R.id.content, mSortFragment, getSupportFragmentManager());
    }

    @Override
    public void onPriceSortSelected(final boolean selected) {
        final List<Product> products;
        if (selected) {
            products = this.sortProductsForPrice(mProducts);
        } else {
            products = mProducts;
        }
        this.startProductSelectionFragment(products);
        mSortPriceSelected = selected;
    }

    @Override
    public void onAlphabeticalSortSelected(final boolean selected) {
        final List<Product> products;
        if (selected) {
            products = this.sortProductsAlphabetically(mProducts);
        } else {
            products = mProducts;
        }
        this.startProductSelectionFragment(products);
        mSortAlphabeticalSelected = selected;
    }

    @Override
    public void onConditionSortSelected(boolean selected) {
        final List<Product> products;
        if (selected) {
            products = this.sortProductsForCondition(mProducts);
        } else {
            products = mProducts;
        }
        this.startProductSelectionFragment(products);
        mSortConditionSelected = selected;
    }

    @Override
    public void onSortFinish() {
        FragmentHelper.removeFragment(mSortFragment, getSupportFragmentManager());
    }

    @NonNull
    private List<Product> sortProductsForPrice(@NonNull final List<Product> products) {
        Collections.sort(products, new ProductPriceComparator());
        return products;
    }

    @NonNull
    private List<Product> sortProductsAlphabetically(@NonNull final List<Product> products) {
        Collections.sort(products, new ProductAlphabetComparator());
        return products;
    }

    @NonNull
    private List<Product> sortProductsForCondition(@NonNull final List<Product> products) {
        Collections.sort(products, new ProductConditionComparator());
        return products;
    }

    private void startProductSelectionFragment(@Nullable final List<Product> products) {
        ProductSelectionFragment selectionFragment = (ProductSelectionFragment) getSupportFragmentManager().findFragmentByTag(TAG_PRODUCT_SELECTION);
        if (null == selectionFragment) {
            if (products != null) {
                selectionFragment = ProductSelectionFragment_.builder().mProductsParcelable(Parcels.wrap(List.class, products)).build();
            } else {
                selectionFragment = ProductSelectionFragment_.builder().build();
            }
            FragmentHelper.replaceFragmentWithTag(R.id.activity_product_selection_main_container, selectionFragment, getSupportFragmentManager(), TAG_PRODUCT_SELECTION);
        } else {
            selectionFragment.updateAdapter(products);
        }
    }

    @OptionsItem(android.R.id.home)
    void goUp() {
        finish();
    }

    @Override
    public void onListEndReached(int pageCounter) {
        String categoryId = "";
        final FairmondoCategory category = mApp.getLastCategory();
        if (category != null) {
            categoryId = category.getId();
        }
        mRestCommunicator.getProducts("", categoryId, pageCounter);
    }

    /**
     * Class for comparing products according to their prices.
     */
    private final class ProductPriceComparator implements Comparator<Product> {
        @Override
        public int compare(final Product leftProduct, final Product rightProduct) {
            return leftProduct.getPriceCents().compareTo(rightProduct.getPriceCents());
        }
    }

    /**
     * Class for comparing product titles alphabetically.
     */
    private final class ProductAlphabetComparator implements Comparator<Product> {
        @Override
        public int compare(final Product leftProduct, final Product rightProduct) {
            return leftProduct.getTitle().compareTo(rightProduct.getTitle());
        }
    }

    /**
     * Class for comparing product titles alphabetically.
     */
    private final class ProductConditionComparator implements Comparator<Product> {
        @Override
        public int compare(final Product leftProduct, final Product rightProduct) {
            if (null != leftProduct.getTags() && null != rightProduct.getTags()) {
                return leftProduct.getTags().getCondition().compareTo(rightProduct.getTags().getCondition());
            }
            return 0;
        }
    }
}
